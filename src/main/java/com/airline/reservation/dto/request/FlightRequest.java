package com.airline.reservation.dto.request;

import com.airline.reservation.enums.FlightStatus;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class FlightRequest {

    @NotBlank(message = "Flight number is required")
    private String flightNumber;

    @NotNull(message = "Aircraft ID is required")
    @Positive
    private Long aircraftId;

    @NotNull(message = "Origin airport ID is required")
    @Positive
    private Long originAirportId;

    @NotNull(message = "Destination airport ID is required")
    @Positive
    private Long destinationAirportId;

    @NotNull(message = "Departure time is required")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    private LocalDateTime arrivalTime;

    @NotNull(message = "Base price is required")
    @Positive(message = "Base price must be positive")
    private BigDecimal basePrice;

    @NotNull(message = "Flight status is required")
    private FlightStatus status;
}
