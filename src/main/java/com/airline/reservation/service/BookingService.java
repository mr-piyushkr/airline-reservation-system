package com.airline.reservation.service;

import com.airline.reservation.dto.request.BookingRequest;
import com.airline.reservation.dto.response.BookingResponse;
import com.airline.reservation.dto.response.BookingSummaryResponse;
import com.airline.reservation.enums.BookingStatus;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request);
    BookingResponse getBookingById(Long id);
    BookingResponse getBookingByReference(String bookingReference);
    List<BookingSummaryResponse> getBookingsByUser(Long userId, BookingStatus status);
    BookingResponse cancelBooking(Long id);
    boolean isOwner(Long bookingId, Long userId);
}
