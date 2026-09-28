package com.airline.reservation.service;

import com.airline.reservation.dto.request.AirportRequest;
import com.airline.reservation.dto.response.AirportResponse;

import java.util.List;

public interface AirportService {
    AirportResponse createAirport(AirportRequest request);
    AirportResponse getAirportById(Long id);
    AirportResponse getAirportByIataCode(String iataCode);
    List<AirportResponse> getAllAirports();
    AirportResponse updateAirport(Long id, AirportRequest request);
    void deleteAirport(Long id);
}
