package com.library.service;

import java.util.List;

import com.library.entity.Book;

public interface BookService {

    Book saveBook(Book book);

    Book getBookById(Integer bookId);

    List<Book> getAllBooks();

    void deleteBook(Integer bookId);

    List<Book> searchBooks(String keyword);

    List<Book> getBooksByCategory(String category);
}