package com.library.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.library.entity.ContactQuery;
import com.library.entity.User;
import com.library.repository.UserRepository;
import com.library.service.ContactQueryService;

@Controller
public class ContactQueryController {

    private final ContactQueryService contactQueryService;
    private final UserRepository userRepository;

    public ContactQueryController(
            ContactQueryService contactQueryService,
            UserRepository userRepository) {

        this.contactQueryService = contactQueryService;
        this.userRepository = userRepository;
    }

    @GetMapping("/contact")
    public String contactPage() {

        return "contact";
    }

    @PostMapping("/contact")
    public String submitQuery(
            ContactQuery query,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user == null) {
            return "redirect:/login";
        }

        query.setUserId(user.getUserId());
        query.setName(user.getFullName());
        query.setEmail(user.getEmail());
        query.setSubmittedAt(LocalDateTime.now());

        contactQueryService.saveQuery(query);

        return "redirect:/contact?success=true";
    }
    
    @GetMapping("/admin/queries")
    public String adminQueries(Model model) {

        List<ContactQuery> queries =
                contactQueryService.getAllQueries();

        model.addAttribute("queries", queries);

        return "admin-queries";
    }
}
