package com.library.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.library.entity.Book;
import com.library.entity.BookIssue;
import com.library.entity.BookReservation;
import com.library.entity.Fine;
import com.library.entity.User;
import com.library.repository.UserRepository;
import com.library.service.BookIssueService;
import com.library.service.BookReservationService;
import com.library.service.BookService;
import com.library.service.FineService;

@Controller
public class BookIssueController {

	private static final BigDecimal FINE_PER_DAY = BigDecimal.valueOf(5);

	private final BookIssueService bookIssueService;
	private final BookService bookService;
	private final UserRepository userRepository;
	private final FineService fineService;
	private final BookReservationService bookReservationService;

	public BookIssueController(BookIssueService bookIssueService, BookService bookService,
			UserRepository userRepository, FineService fineService, BookReservationService bookReservationService) {

		this.bookIssueService = bookIssueService;
		this.bookService = bookService;
		this.userRepository = userRepository;
		this.fineService = fineService;
		this.bookReservationService = bookReservationService;
	}

	@GetMapping("/issue-book/{bookId}")
	public String issueBook(@PathVariable Integer bookId, Authentication authentication) {

		String email = authentication.getName();

		User user = userRepository.findByEmail(email).orElse(null);

		if (user == null) {
			return "redirect:/login";
		}

		Book book = bookService.getBookById(bookId);

		if (book != null && book.getAvailableQuantity() > 0) {

			BookIssue bookIssue = new BookIssue();

			bookIssue.setUserId(user.getUserId());
			bookIssue.setBookId(bookId);

			bookIssue.setIssueDate(LocalDate.now());

			bookIssue.setDueDate(LocalDate.now().plusDays(7));

			bookIssue.setStatus("ISSUED");

			bookIssueService.saveBookIssue(bookIssue);

			book.setAvailableQuantity(book.getAvailableQuantity() - 1);

			bookService.saveBook(book);
			
			BookReservation readyReservation =
			        bookReservationService.getReservationsByUserId(user.getUserId())
			                .stream()
			                .filter(r -> r.getBookId().equals(bookId)
			                        && "READY".equals(r.getStatus()))
			                .findFirst()
			                .orElse(null);

			if (readyReservation != null) {
			    bookReservationService.markReservationCompleted(
			            readyReservation.getReservationId());
			}
		}

		return "redirect:/books";
	}

	@GetMapping("/my-issues")
	public String myIssues(Authentication authentication, Model model) {

		String email = authentication.getName();

		User user = userRepository.findByEmail(email).orElse(null);

		if (user == null) {
			return "redirect:/login";
		}

		List<BookIssue> issues = bookIssueService.getBookIssuesByUser(user.getUserId());

		model.addAttribute("issues", issues);

		return "my-issues";
	}

	// ================= RETURN BOOK =================

	@GetMapping("/return-book/{issueId}")
	public String returnBook(@PathVariable Integer issueId, Authentication authentication) {

		String email = authentication.getName();

		User user = userRepository.findByEmail(email).orElse(null);

		if (user == null) {
			return "redirect:/login";
		}

		BookIssue bookIssue = bookIssueService.getBookIssueById(issueId);

		if (bookIssue == null) {
			return "redirect:/my-issues";
		}

		// Make sure user owns this issue
		if (!bookIssue.getUserId().equals(user.getUserId())) {
			return "redirect:/my-issues";
		}

		// Only issued books can be returned
		if ("ISSUED".equals(bookIssue.getStatus())) {

			LocalDate returnDate = LocalDate.now();

			bookIssue.setStatus("RETURNED");
			bookIssue.setReturnDate(returnDate);


			long lateDays = ChronoUnit.DAYS.between(bookIssue.getDueDate(), returnDate);

			if (lateDays > 0) {

				Fine existingFine = fineService.getFineByIssueId(issueId);

				if (existingFine == null) {

					BigDecimal fineAmount = FINE_PER_DAY.multiply(BigDecimal.valueOf(lateDays));

					Fine fine = new Fine();

					fine.setIssueId(issueId);
					fine.setUserId(user.getUserId());
					fine.setFineAmount(fineAmount);
					fine.setPaidStatus("PENDING");

					fineService.saveFine(fine);
				}
			}

			bookIssueService.saveBookIssue(bookIssue);

			Book book = bookService.getBookById(bookIssue.getBookId());

			if (book != null) {

				book.setAvailableQuantity(book.getAvailableQuantity() + 1);

				bookService.saveBook(book);
			}
			
			BookReservation approvedReservation =
			        bookReservationService
			                .getApprovedReservationByBookId(bookIssue.getBookId());

			if (approvedReservation != null) {

			    approvedReservation.setStatus("READY");

			    bookReservationService.saveReservation(approvedReservation);
			}
		}

		return "redirect:/my-issues";
	}

	@GetMapping("/admin/issues")
	public String adminIssues(Model model) {

		List<BookIssue> issues = bookIssueService.getAllBookIssue();

		Map<Integer, User> users = new HashMap<>();
		Map<Integer, Book> books = new HashMap<>();

		for (BookIssue issue : issues) {

			Integer userId = issue.getUserId();
			Integer bookId = issue.getBookId();

			if (!users.containsKey(userId)) {

				User user = userRepository.findById(userId).orElse(null);

				users.put(userId, user);
			}

			if (!books.containsKey(bookId)) {

				Book book = bookService.getBookById(bookId);

				books.put(bookId, book);
			}
		}

		model.addAttribute("issues", issues);
		model.addAttribute("users", users);
		model.addAttribute("books", books);

		return "admin-issues";
	}
	
	@GetMapping("/issue-reserved-book/{reservationId}")
	public String issueReservedBook(
	        @PathVariable Integer reservationId,
	        Authentication authentication) {

	    String email = authentication.getName();

	    User user = userRepository.findByEmail(email).orElse(null);

	    if (user == null) {
	        return "redirect:/login";
	    }

	    BookReservation reservation =
	            bookReservationService.getReservationById(reservationId);

	    if (reservation == null) {
	        return "redirect:/my-reservations";
	    }

	    if (!reservation.getUserId().equals(user.getUserId())) {
	        return "redirect:/my-reservations";
	    }

	    if (!"READY".equals(reservation.getStatus())) {
	        return "redirect:/my-reservations";
	    }

	    Book book = bookService.getBookById(reservation.getBookId());

	    if (book == null) {
	        return "redirect:/my-reservations";
	    }

	    if (book.getAvailableQuantity() <= 0) {
	        return "redirect:/my-reservations";
	    }

	    BookIssue bookIssue = new BookIssue();

	    bookIssue.setUserId(user.getUserId());
	    bookIssue.setBookId(book.getBookId());
	    bookIssue.setIssueDate(LocalDate.now());
	    bookIssue.setDueDate(LocalDate.now().plusDays(7));
	    bookIssue.setStatus("ISSUED");

	    bookIssueService.saveBookIssue(bookIssue);

	    book.setAvailableQuantity(
	            book.getAvailableQuantity() - 1);

	    bookService.saveBook(book);

	    reservation.setStatus("COMPLETED");

	    bookReservationService.saveReservation(reservation);

	    return "redirect:/my-issues";
	}
}