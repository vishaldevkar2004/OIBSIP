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

import com.library.entity.BookIssue;
import com.library.entity.Fine;
import com.library.entity.User;
import com.library.repository.UserRepository;
import com.library.service.BookIssueService;
import com.library.service.FineService;

@Controller
public class FineController {

    private static final BigDecimal FINE_PER_DAY = BigDecimal.valueOf(5);

    private final FineService fineService;
    private final BookIssueService bookIssueService;
    private final UserRepository userRepository;

    public FineController(
            FineService fineService,
            BookIssueService bookIssueService,
            UserRepository userRepository) {

        this.fineService = fineService;
        this.bookIssueService = bookIssueService;
        this.userRepository = userRepository;
    }

    @GetMapping("/generate-fine/{issueId}")
    public String generateFine(
            @PathVariable Integer issueId,
            Authentication authentication) {

        BookIssue issue = bookIssueService.getBookIssueById(issueId);

        if (issue == null) {
            return "redirect:/my-issues";
        }

        User user = userRepository.findById(issue.getUserId())
                .orElse(null);

        if (user == null) {
            return "redirect:/login";
        }

        if (!user.getEmail().equals(authentication.getName())) {
            return "redirect:/my-issues";
        }

        LocalDate returnDate = issue.getReturnDate();

        if (returnDate == null) {
            return "redirect:/my-issues";
        }

        Fine existingFine = fineService.getFineByIssueId(issueId);

        if (existingFine != null) {
            return "redirect:/my-fines";
        }

        long lateDays = ChronoUnit.DAYS.between(
                issue.getDueDate(),
                returnDate
        );

        if (lateDays <= 0) {
            return "redirect:/my-fines";
        }

        BigDecimal amount = FINE_PER_DAY.multiply(
                BigDecimal.valueOf(lateDays)
        );

        Fine fine = new Fine();

        fine.setIssueId(issueId);
        fine.setUserId(issue.getUserId());
        fine.setFineAmount(amount);
        fine.setPaidStatus("PENDING");

        fineService.saveFine(fine);

        return "redirect:/my-fines";
    }
    
    @GetMapping("/my-fines")
    public String myFines(
            Authentication authentication,
            Model model) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user == null) {
            return "redirect:/login";
        }

        List<Fine> fines =
                fineService.getFinesByUserId(user.getUserId());

        model.addAttribute("fines", fines);

        return "my-fines";
    }
    
    @GetMapping("/admin/fines")
    public String adminFines(Model model) {

        List<Fine> fines = fineService.getAllFines();

        Map<Integer, User> users = new HashMap<>();

        for (Fine fine : fines) {

            Integer userId = fine.getUserId();

            if (!users.containsKey(userId)) {

                User user = userRepository.findById(userId)
                        .orElse(null);

                users.put(userId, user);
            }
        }

        model.addAttribute("fines", fines);
        model.addAttribute("users", users);

        return "admin-fines";
    }
    
    @GetMapping("/admin/pay-fine/{fineId}")
    public String markFineAsPaid(
            @PathVariable Integer fineId) {

        Fine fine = fineService.getFineById(fineId);

        if (fine != null) {

            fine.setPaidStatus("PAID");

            fineService.saveFine(fine);
        }

        return "redirect:/admin/fines";
    }    
}