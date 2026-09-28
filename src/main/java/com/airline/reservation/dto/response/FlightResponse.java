package com.airline.reservation.dto.response;

import com.airline.reservation.enums.FlightStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class FlightResponse {
    private Long id;
    private String flightNumber;
    private AircraftResponse aircraft;
    private AirportResponse originAirport;
    private AirportResponse destinationAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private BigDecimal basePrice;
    private FlightStatus status;
    private LocalDateTime createdAt;
}
