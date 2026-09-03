package com.library.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.library.entity.BookReservation;
import com.library.repository.BookReservationRepository;

@Service
public class BookReservationServiceImpl implements BookReservationService {

	private final BookReservationRepository bookReservationRepository;

	public BookReservationServiceImpl(BookReservationRepository bookReservationRepository) {

		this.bookReservationRepository = bookReservationRepository;
	}

	@Override
	public BookReservation saveReservation(BookReservation reservation) {

		boolean alreadyReserved = bookReservationRepository.existsByUserIdAndBookIdAndStatus(reservation.getUserId(),
				reservation.getBookId(), "PENDING");

		if (alreadyReserved) {
			return null;
		}

		return bookReservationRepository.save(reservation);
	}

	@Override
	public BookReservation getReservationById(Integer reservationId) {
		return bookReservationRepository.findById(reservationId).orElse(null);
	}

	@Override
	public List<BookReservation> getAllReservations() {
		return bookReservationRepository.findAll();
	}

	@Override
	public void deleteReservation(Integer reservationId) {
		bookReservationRepository.deleteById(reservationId);
	}

	@Override
	public List<BookReservation> getReservationsByUserId(Integer userId) {
		return bookReservationRepository.findByUserId(userId);
	}

	@Override
	public List<BookReservation> getReservationsByBookId(Integer bookId) {
		return bookReservationRepository.findByBookId(bookId);
	}

	@Override
	public boolean isAlreadyReserved(Integer userId, Integer bookId, String status) {

		return bookReservationRepository.existsByUserIdAndBookIdAndStatus(userId, bookId, status);
	}

	@Override
	public void approveReservation(Integer reservationId) {

		BookReservation reservation = bookReservationRepository.findById(reservationId).orElse(null);

		if (reservation != null) {
			reservation.setStatus("APPROVED");
			bookReservationRepository.save(reservation);
		}
	}

	@Override
	public void cancelReservation(Integer reservationId) {

		BookReservation reservation = bookReservationRepository.findById(reservationId).orElse(null);

		if (reservation != null) {
			reservation.setStatus("CANCELLED");
			bookReservationRepository.save(reservation);
		}
	}
	@Override
	public BookReservation getApprovedReservationByBookId(Integer bookId) {

	    return bookReservationRepository
	            .findFirstByBookIdAndStatusOrderByReservationDateAsc(
	                    bookId,
	                    "APPROVED");
	}
	
	@Override
	public void markReservationReady(Integer reservationId) {

	    BookReservation reservation =
	            bookReservationRepository
	                    .findById(reservationId)
	                    .orElse(null);

	    if (reservation != null) {
	        reservation.setStatus("READY");
	        bookReservationRepository.save(reservation);
	    }
	}
	
    @Override
    public void markReservationCompleted(Integer reservationId) {

        BookReservation reservation =
                bookReservationRepository
                        .findById(reservationId)
                        .orElse(null);

        if (reservation != null) {

            reservation.setStatus("COMPLETED");

            bookReservationRepository.save(reservation);
        }
    }
}