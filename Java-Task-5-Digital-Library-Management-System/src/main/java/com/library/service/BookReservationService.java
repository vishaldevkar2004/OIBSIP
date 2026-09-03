package com.library.service;

import java.util.List;

import com.library.entity.BookReservation;

public interface BookReservationService {

    BookReservation saveReservation(BookReservation reservation);

    BookReservation getReservationById(Integer reservationId);

    List<BookReservation> getAllReservations();

    void deleteReservation(Integer reservationId);

    List<BookReservation> getReservationsByUserId(Integer userId);

    List<BookReservation> getReservationsByBookId(Integer bookId);

    boolean isAlreadyReserved(Integer userId, Integer bookId, String status);
    
    void approveReservation(Integer reservationId);

    void cancelReservation(Integer reservationId);
    
    BookReservation getApprovedReservationByBookId(Integer bookId);
    
    void markReservationReady(Integer reservationId);
    
    void markReservationCompleted(Integer reservationId);
}