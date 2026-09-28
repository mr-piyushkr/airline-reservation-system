package com.airline.reservation.controller;

import com.airline.reservation.dto.request.PaymentRequest;
import com.airline.reservation.dto.response.PaymentResponse;
import com.airline.reservation.security.AuthenticatedUser;
import com.airline.reservation.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(
            @Valid @RequestBody PaymentRequest request,
            @AuthenticationPrincipal AuthenticatedUser principal) {
        if (!principal.getRole().equals("ADMIN")) {
            Long ownerUserId = paymentService.getBookingUserId(request.getBookingId());
            if (!ownerUserId.equals(principal.getId())) {
                throw new AccessDeniedException("You can only pay for your own bookings");
            }
        }
        return ResponseEntity.ok(paymentService.processPayment(request));
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<PaymentResponse> getPaymentByBooking(
            @PathVariable Long bookingId,
            @AuthenticationPrincipal AuthenticatedUser principal) {
        if (!principal.getRole().equals("ADMIN")) {
            Long ownerUserId = paymentService.getBookingUserId(bookingId);
            if (!ownerUserId.equals(principal.getId())) {
                throw new AccessDeniedException("You can only view payments for your own bookings");
            }
        }
        return ResponseEntity.ok(paymentService.getPaymentByBookingId(bookingId));
    }
}
