package com.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.entity.BookReservation;

public interface BookReservationRepository extends JpaRepository<BookReservation, Integer> {

    List<BookReservation> findByUserId(Integer userId);

    List<BookReservation> findByBookId(Integer bookId);

    boolean existsByUserIdAndBookIdAndStatus(
            Integer userId,
            Integer bookId,
            String status);
    
    BookReservation findFirstByBookIdAndStatusOrderByReservationDateAsc(
            Integer bookId,
            String status);
}