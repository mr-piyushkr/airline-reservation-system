package com.airline.reservation.mapper;

import com.airline.reservation.dto.request.AirportRequest;
import com.airline.reservation.dto.response.AirportResponse;
import com.airline.reservation.entity.Airport;
import org.springframework.stereotype.Component;

@Component
public class AirportMapper {

    public Airport toEntity(AirportRequest request) {
        return Airport.builder()
                .iataCode(request.getIataCode())
                .name(request.getName())
                .city(request.getCity())
                .country(request.getCountry())
                .timezone(request.getTimezone())
                .build();
    }

    public AirportResponse toResponse(Airport airport) {
        return AirportResponse.builder()
                .id(airport.getId())
                .iataCode(airport.getIataCode())
                .name(airport.getName())
                .city(airport.getCity())
                .country(airport.getCountry())
                .timezone(airport.getTimezone())
                .build();
    }
}
