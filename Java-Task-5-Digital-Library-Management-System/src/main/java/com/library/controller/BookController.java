package com.library.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.library.entity.Book;
import com.library.service.BookService;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public String viewBooks(
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        List<Book> books;

        if (keyword != null && !keyword.trim().isEmpty()) {

            books = bookService.searchBooks(keyword);

            model.addAttribute("keyword", keyword);

        } else {

            books = bookService.getAllBooks();
        }

        model.addAttribute("books", books);

        return "books";
    }

    @GetMapping("/admin/edit-book/{bookId}")
    public String editBook(
            @PathVariable Integer bookId,
            Model model) {

        Book book = bookService.getBookById(bookId);

        model.addAttribute("book", book);

        return "edit-book";
    }

    @PostMapping("/admin/edit-book")
    public String updateBook(Book book) {

        bookService.saveBook(book);

        return "redirect:/books";
    }

    @GetMapping("/admin/delete-book/{bookId}")
    public String deleteBook(
            @PathVariable Integer bookId) {

        bookService.deleteBook(bookId);

        return "redirect:/books";
    }
}