package com.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.library.entity.Book;
import com.library.service.BookService;

@Controller
public class AdminController {

    private final BookService bookService;

    public AdminController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/admin-dashboard")
    public String adminDashboard() {
        return "admin-dashboard";
    }

    @GetMapping("/admin/add-book")
    public String addBookPage(Model model) {

        model.addAttribute("book", new Book());

        return "add-book";
    }

    @PostMapping("/admin/add-book")
    public String saveBook(@ModelAttribute("book") Book book) {

        book.setAvailableQuantity(book.getQuantity());

        bookService.saveBook(book);

        return "redirect:/books";
    }
}