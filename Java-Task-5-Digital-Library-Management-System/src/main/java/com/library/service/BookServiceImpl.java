package com.library.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.library.entity.Book;
import com.library.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService
{
	private final BookRepository bookRepository;
	
	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public Book saveBook(Book book) {
		return bookRepository.save(book);
	}

	@Override
	public Book getBookById(Integer bookId) {
		return bookRepository.findById(bookId).orElse(null);
	}

	@Override
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public void deleteBook(Integer bookId) {
		bookRepository.deleteById(bookId);
	}

	@Override
	public List<Book> searchBooks(String keyword) {
		return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(keyword, keyword);
	}

	@Override
	public List<Book> getBooksByCategory(String category) {
		return bookRepository.findByCategoryIgnoreCase(category);
	}	
}
