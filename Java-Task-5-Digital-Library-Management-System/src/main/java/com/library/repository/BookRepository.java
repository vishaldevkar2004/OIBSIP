package com.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(
            String title,
            String author
    );

    List<Book> findByCategoryIgnoreCase(String category);
}