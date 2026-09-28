package com.airline.reservation.service;

import com.airline.reservation.dto.request.PaymentRequest;
import com.airline.reservation.dto.response.PaymentResponse;

public interface PaymentService {
    PaymentResponse processPayment(PaymentRequest request);
    PaymentResponse getPaymentByBookingId(Long bookingId);
    Long getBookingUserId(Long bookingId);
}
