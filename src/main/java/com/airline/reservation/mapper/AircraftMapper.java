package com.airline.reservation.mapper;

import com.airline.reservation.dto.request.AircraftRequest;
import com.airline.reservation.dto.response.AircraftResponse;
import com.airline.reservation.entity.Aircraft;
import org.springframework.stereotype.Component;

@Component
public class AircraftMapper {

    public Aircraft toEntity(AircraftRequest request) {
        return Aircraft.builder()
                .registrationNumber(request.getRegistrationNumber())
                .model(request.getModel())
                .manufacturer(request.getManufacturer())
                .totalSeats(request.getTotalSeats())
                .economySeats(request.getEconomySeats())
                .businessSeats(request.getBusinessSeats())
                .firstClassSeats(request.getFirstClassSeats())
                .active(true)
                .build();
    }

    public AircraftResponse toResponse(Aircraft aircraft) {
        return AircraftResponse.builder()
                .id(aircraft.getId())
                .registrationNumber(aircraft.getRegistrationNumber())
                .model(aircraft.getModel())
                .manufacturer(aircraft.getManufacturer())
                .totalSeats(aircraft.getTotalSeats())
                .economySeats(aircraft.getEconomySeats())
                .businessSeats(aircraft.getBusinessSeats())
                .firstClassSeats(aircraft.getFirstClassSeats())
                .active(aircraft.isActive())
                .build();
    }
}
