package com.airline.reservation.service.impl;

import com.airline.reservation.dto.request.FlightRequest;
import com.airline.reservation.dto.response.FlightResponse;
import com.airline.reservation.entity.Aircraft;
import com.airline.reservation.entity.Airport;
import com.airline.reservation.entity.Flight;
import com.airline.reservation.exception.BadRequestException;
import com.airline.reservation.exception.DuplicateResourceException;
import com.airline.reservation.exception.ResourceNotFoundException;
import com.airline.reservation.mapper.FlightMapper;
import com.airline.reservation.repository.AircraftRepository;
import com.airline.reservation.repository.AirportRepository;
import com.airline.reservation.repository.FlightRepository;
import com.airline.reservation.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final AircraftRepository aircraftRepository;
    private final AirportRepository airportRepository;
    private final FlightMapper flightMapper;

    @Override
    public FlightResponse createFlight(FlightRequest request) {
        if (flightRepository.existsByFlightNumber(request.getFlightNumber())) {
            throw new DuplicateResourceException("Flight number already exists: " + request.getFlightNumber());
        }
        if (!request.getArrivalTime().isAfter(request.getDepartureTime())) {
            throw new BadRequestException("Arrival time must be after departure time");
        }
        Aircraft aircraft = aircraftRepository.findById(request.getAircraftId())
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found with id: " + request.getAircraftId()));
        Airport origin = airportRepository.findById(request.getOriginAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Origin airport not found with id: " + request.getOriginAirportId()));
        Airport destination = airportRepository.findById(request.getDestinationAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Destination airport not found with id: " + request.getDestinationAirportId()));
        if (origin.getId().equals(destination.getId())) {
            throw new BadRequestException("Origin and destination airports cannot be the same");
        }
        Flight saved = flightRepository.save(flightMapper.toEntity(request, aircraft, origin, destination));
        return flightMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public FlightResponse getFlightById(Long id) {
        return flightMapper.toResponse(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public FlightResponse getFlightByNumber(String flightNumber) {
        Flight flight = flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with number: " + flightNumber));
        return flightMapper.toResponse(flight);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FlightResponse> getAllFlights() {
        return flightRepository.findAll().stream().map(flightMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FlightResponse> searchFlights(String origin, String destination, LocalDate date) {
        LocalDateTime from = date.atStartOfDay();
        LocalDateTime to = date.plusDays(1).atStartOfDay();
        return flightRepository.searchFlights(origin, destination, from, to)
                .stream().map(flightMapper::toResponse).toList();
    }

    @Override
    public FlightResponse updateFlight(Long id, FlightRequest request) {
        Flight flight = findById(id);
        if (!flight.getFlightNumber().equals(request.getFlightNumber())
                && flightRepository.existsByFlightNumber(request.getFlightNumber())) {
            throw new DuplicateResourceException("Flight number already exists: " + request.getFlightNumber());
        }
        if (!request.getArrivalTime().isAfter(request.getDepartureTime())) {
            throw new BadRequestException("Arrival time must be after departure time");
        }
        Aircraft aircraft = aircraftRepository.findById(request.getAircraftId())
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found with id: " + request.getAircraftId()));
        Airport origin = airportRepository.findById(request.getOriginAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Origin airport not found with id: " + request.getOriginAirportId()));
        Airport destination = airportRepository.findById(request.getDestinationAirportId())
                .orElseThrow(() -> new ResourceNotFoundException("Destination airport not found with id: " + request.getDestinationAirportId()));
        flight.setFlightNumber(request.getFlightNumber());
        flight.setAircraft(aircraft);
        flight.setOriginAirport(origin);
        flight.setDestinationAirport(destination);
        flight.setDepartureTime(request.getDepartureTime());
        flight.setArrivalTime(request.getArrivalTime());
        flight.setBasePrice(request.getBasePrice());
        flight.setStatus(request.getStatus());
        return flightMapper.toResponse(flightRepository.save(flight));
    }

    @Override
    public void deleteFlight(Long id) {
        findById(id);
        flightRepository.deleteById(id);
    }

    private Flight findById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id));
    }
}
