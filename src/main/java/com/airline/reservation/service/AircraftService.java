package com.airline.reservation.service;

import com.airline.reservation.dto.request.AircraftRequest;
import com.airline.reservation.dto.response.AircraftResponse;

import java.util.List;

public interface AircraftService {
    AircraftResponse createAircraft(AircraftRequest request);
    AircraftResponse getAircraftById(Long id);
    List<AircraftResponse> getAllAircraft();
    AircraftResponse updateAircraft(Long id, AircraftRequest request);
    void deleteAircraft(Long id);
}
