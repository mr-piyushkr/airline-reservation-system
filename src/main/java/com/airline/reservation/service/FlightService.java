package com.airline.reservation.service;

import com.airline.reservation.dto.request.FlightRequest;
import com.airline.reservation.dto.response.FlightResponse;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {
    FlightResponse createFlight(FlightRequest request);
    FlightResponse getFlightById(Long id);
    FlightResponse getFlightByNumber(String flightNumber);
    List<FlightResponse> getAllFlights();
    List<FlightResponse> searchFlights(String origin, String destination, LocalDate date);
    FlightResponse updateFlight(Long id, FlightRequest request);
    void deleteFlight(Long id);
}
