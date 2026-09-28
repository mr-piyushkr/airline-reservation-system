package com.airline.reservation.mapper;

import com.airline.reservation.dto.request.PassengerRequest;
import com.airline.reservation.dto.response.PassengerResponse;
import com.airline.reservation.entity.Passenger;
import com.airline.reservation.entity.User;
import org.springframework.stereotype.Component;

@Component
public class PassengerMapper {

    public Passenger toEntity(PassengerRequest request, User user) {
        return Passenger.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .passportNumber(request.getPassportNumber())
                .dateOfBirth(request.getDateOfBirth())
                .nationality(request.getNationality())
                .user(user)
                .build();
    }

    public PassengerResponse toResponse(Passenger passenger) {
        return PassengerResponse.builder()
                .id(passenger.getId())
                .firstName(passenger.getFirstName())
                .lastName(passenger.getLastName())
                .passportNumber(passenger.getPassportNumber())
                .dateOfBirth(passenger.getDateOfBirth())
                .nationality(passenger.getNationality())
                .userId(passenger.getUser() != null ? passenger.getUser().getId() : null)
                .createdAt(passenger.getCreatedAt())
                .build();
    }
}
