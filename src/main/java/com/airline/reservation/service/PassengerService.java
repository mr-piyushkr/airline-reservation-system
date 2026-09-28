package com.airline.reservation.service;

import com.airline.reservation.dto.request.PassengerRequest;
import com.airline.reservation.dto.response.PassengerResponse;

import java.util.List;

public interface PassengerService {
    PassengerResponse createPassenger(PassengerRequest request);
    PassengerResponse getPassengerById(Long id);
    PassengerResponse getPassengerByPassportNumber(String passportNumber);
    List<PassengerResponse> getPassengersByUser(Long userId);
    PassengerResponse updatePassenger(Long id, PassengerRequest request);
}
