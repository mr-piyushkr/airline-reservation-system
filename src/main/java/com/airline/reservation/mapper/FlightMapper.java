package com.airline.reservation.mapper;

import com.airline.reservation.dto.request.FlightRequest;
import com.airline.reservation.dto.response.FlightResponse;
import com.airline.reservation.entity.Aircraft;
import com.airline.reservation.entity.Airport;
import com.airline.reservation.entity.Flight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlightMapper {

    private final AircraftMapper aircraftMapper;
    private final AirportMapper airportMapper;

    public Flight toEntity(FlightRequest request, Aircraft aircraft, Airport origin, Airport destination) {
        return Flight.builder()
                .flightNumber(request.getFlightNumber())
                .aircraft(aircraft)
                .originAirport(origin)
                .destinationAirport(destination)
                .departureTime(request.getDepartureTime())
                .arrivalTime(request.getArrivalTime())
                .basePrice(request.getBasePrice())
                .status(request.getStatus())
                .build();
    }

    public FlightResponse toResponse(Flight flight) {
        return FlightResponse.builder()
                .id(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .aircraft(aircraftMapper.toResponse(flight.getAircraft()))
                .originAirport(airportMapper.toResponse(flight.getOriginAirport()))
                .destinationAirport(airportMapper.toResponse(flight.getDestinationAirport()))
                .departureTime(flight.getDepartureTime())
                .arrivalTime(flight.getArrivalTime())
                .basePrice(flight.getBasePrice())
                .status(flight.getStatus())
                .createdAt(flight.getCreatedAt())
                .build();
    }
}
