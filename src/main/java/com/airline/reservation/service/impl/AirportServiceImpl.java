package com.airline.reservation.service.impl;

import com.airline.reservation.dto.request.AirportRequest;
import com.airline.reservation.dto.response.AirportResponse;
import com.airline.reservation.entity.Airport;
import com.airline.reservation.exception.DuplicateResourceException;
import com.airline.reservation.exception.ResourceNotFoundException;
import com.airline.reservation.mapper.AirportMapper;
import com.airline.reservation.repository.AirportRepository;
import com.airline.reservation.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;
    private final AirportMapper airportMapper;

    @Override
    public AirportResponse createAirport(AirportRequest request) {
        if (airportRepository.existsByIataCode(request.getIataCode())) {
            throw new DuplicateResourceException("Airport with IATA code already exists: " + request.getIataCode());
        }
        return airportMapper.toResponse(airportRepository.save(airportMapper.toEntity(request)));
    }

    @Override
    @Transactional(readOnly = true)
    public AirportResponse getAirportById(Long id) {
        return airportMapper.toResponse(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public AirportResponse getAirportByIataCode(String iataCode) {
        Airport airport = airportRepository.findByIataCode(iataCode)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found with IATA code: " + iataCode));
        return airportMapper.toResponse(airport);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AirportResponse> getAllAirports() {
        return airportRepository.findAll().stream().map(airportMapper::toResponse).toList();
    }

    @Override
    public AirportResponse updateAirport(Long id, AirportRequest request) {
        Airport airport = findById(id);
        if (!airport.getIataCode().equals(request.getIataCode()) && airportRepository.existsByIataCode(request.getIataCode())) {
            throw new DuplicateResourceException("Airport with IATA code already exists: " + request.getIataCode());
        }
        airport.setIataCode(request.getIataCode());
        airport.setName(request.getName());
        airport.setCity(request.getCity());
        airport.setCountry(request.getCountry());
        airport.setTimezone(request.getTimezone());
        return airportMapper.toResponse(airportRepository.save(airport));
    }

    @Override
    public void deleteAirport(Long id) {
        findById(id);
        airportRepository.deleteById(id);
    }

    private Airport findById(Long id) {
        return airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport not found with id: " + id));
    }
}
