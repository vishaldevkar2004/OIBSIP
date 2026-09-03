package com.library.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.library.entity.Book;
import com.library.entity.BookReservation;
import com.library.entity.User;
import com.library.repository.UserRepository;
import com.library.service.BookReservationService;
import com.library.service.BookService;

@Controller
public class BookReservationController {

	private final BookReservationService bookReservationService;
	private final BookService bookService;
	private final UserRepository userRepository;

	public BookReservationController(BookReservationService bookReservationService, BookService bookService,
			UserRepository userRepository) {

		this.bookReservationService = bookReservationService;
		this.bookService = bookService;
		this.userRepository = userRepository;
	}

	@GetMapping("/reserve-book/{bookId}")
	public String reserveBook(@PathVariable Integer bookId, Authentication authentication) {

		String email = authentication.getName();

		User user = userRepository.findByEmail(email).orElse(null);

		if (user == null) {
			return "redirect:/login";
		}

		Book book = bookService.getBookById(bookId);

		if (book == null) {
			return "redirect:/books";
		}

		if (book.getAvailableQuantity() == 0) {

			boolean alreadyReserved = bookReservationService.isAlreadyReserved(user.getUserId(), bookId, "PENDING");

			if (!alreadyReserved) {

				BookReservation reservation = new BookReservation();

				reservation.setUserId(user.getUserId());
				reservation.setBookId(bookId);
				reservation.setReservationDate(LocalDate.now());
				reservation.setStatus("PENDING");

				bookReservationService.saveReservation(reservation);
			}
		}

		return "redirect:/books";
	}

	@GetMapping("/my-reservations")
	public String myReservations(Authentication authentication, Model model) {

		String email = authentication.getName();

		User user = userRepository.findByEmail(email).orElse(null);

		if (user == null) {
			return "redirect:/login";
		}

		List<BookReservation> reservations = bookReservationService.getReservationsByUserId(user.getUserId());

		model.addAttribute("reservations", reservations);

		return "my-reservations";
	}

	@GetMapping("/admin/reservations")
	public String adminReservations(Model model) {

		List<BookReservation> reservations = bookReservationService.getAllReservations();

		model.addAttribute("reservations", reservations);

		return "admin-reservations";
	}

	@GetMapping("/admin/reservation/approve/{reservationId}")
	public String approveReservation(@PathVariable Integer reservationId) {

		bookReservationService.approveReservation(reservationId);

		return "redirect:/admin/reservations";
	}

	@GetMapping("/admin/reservation/cancel/{reservationId}")
	public String cancelReservation(@PathVariable Integer reservationId) {

		bookReservationService.cancelReservation(reservationId);

		return "redirect:/admin/reservations";
	}
}