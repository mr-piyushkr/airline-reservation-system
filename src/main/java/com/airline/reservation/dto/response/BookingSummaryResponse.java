package com.airline.reservation.dto.response;

import com.airline.reservation.enums.BookingStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class BookingSummaryResponse {
    private Long id;
    private String bookingReference;
    private BookingStatus status;
    private String flightNumber;
    private String originIata;
    private String destinationIata;
    private LocalDateTime departureTime;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
}
