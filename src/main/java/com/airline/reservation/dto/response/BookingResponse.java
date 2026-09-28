package com.airline.reservation.dto.response;

import com.airline.reservation.enums.BookingStatus;
import com.airline.reservation.enums.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class BookingResponse {
    private Long id;
    private String bookingReference;
    private BookingStatus status;

    // User info
    private Long userId;
    private String userEmail;

    // Flight info
    private Long flightId;
    private String flightNumber;
    private String originIata;
    private String originCity;
    private String destinationIata;
    private String destinationCity;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    // Passengers
    private List<PassengerResponse> passengers;

    // Seats
    private List<SeatResponse> seats;

    // Fare
    private BigDecimal totalAmount;

    // Payment
    private PaymentStatus paymentStatus;
    private String transactionId;
    private String paymentMethod;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
