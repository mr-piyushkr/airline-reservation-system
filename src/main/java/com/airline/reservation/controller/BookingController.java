package com.airline.reservation.controller;

import com.airline.reservation.dto.request.BookingRequest;
import com.airline.reservation.dto.response.BookingResponse;
import com.airline.reservation.dto.response.BookingSummaryResponse;
import com.airline.reservation.enums.BookingStatus;
import com.airline.reservation.exception.BadRequestException;
import com.airline.reservation.security.AuthenticatedUser;
import com.airline.reservation.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @Valid @RequestBody BookingRequest request,
            @AuthenticationPrincipal AuthenticatedUser principal) {
        // USER can only create booking for themselves
        if (!principal.getRole().equals("ADMIN") && !request.getUserId().equals(principal.getId())) {
            throw new AccessDeniedException("You can only create bookings for yourself");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(
            @PathVariable Long id,
            @AuthenticationPrincipal AuthenticatedUser principal) {
        if (!principal.getRole().equals("ADMIN") && !bookingService.isOwner(id, principal.getId())) {
            throw new AccessDeniedException("Access denied to this booking");
        }
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping("/reference/{bookingReference}")
    public ResponseEntity<BookingResponse> getBookingByReference(
            @PathVariable String bookingReference,
            @AuthenticationPrincipal AuthenticatedUser principal) {
        BookingResponse booking = bookingService.getBookingByReference(bookingReference);
        if (!principal.getRole().equals("ADMIN") && !booking.getUserId().equals(principal.getId())) {
            throw new AccessDeniedException("Access denied to this booking");
        }
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingSummaryResponse>> getBookingsByUser(
            @PathVariable Long userId,
            @RequestParam(required = false) BookingStatus status,
            @AuthenticationPrincipal AuthenticatedUser principal) {
        // USER can only see their own bookings
        if (!principal.getRole().equals("ADMIN") && !userId.equals(principal.getId())) {
            throw new AccessDeniedException("You can only view your own bookings");
        }
        return ResponseEntity.ok(bookingService.getBookingsByUser(userId, status));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(
            @PathVariable Long id,
            @AuthenticationPrincipal AuthenticatedUser principal) {
        if (!principal.getRole().equals("ADMIN") && !bookingService.isOwner(id, principal.getId())) {
            throw new AccessDeniedException("You can only cancel your own bookings");
        }
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }
}
