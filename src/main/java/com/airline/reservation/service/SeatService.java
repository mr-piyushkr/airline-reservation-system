package com.airline.reservation.service;

import com.airline.reservation.dto.request.SeatRequest;
import com.airline.reservation.dto.response.SeatResponse;
import com.airline.reservation.enums.SeatClass;

import java.util.List;

public interface SeatService {
    SeatResponse createSeat(SeatRequest request);
    List<SeatResponse> getSeatsByFlight(Long flightId);
    List<SeatResponse> getAvailableSeatsByFlight(Long flightId);
    List<SeatResponse> getSeatsByFlightAndClass(Long flightId, SeatClass seatClass);
    SeatResponse getSeatById(Long id);
}
