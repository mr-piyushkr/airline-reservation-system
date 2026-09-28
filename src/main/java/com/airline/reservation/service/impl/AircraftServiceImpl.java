package com.airline.reservation.service.impl;

import com.airline.reservation.dto.request.AircraftRequest;
import com.airline.reservation.dto.response.AircraftResponse;
import com.airline.reservation.entity.Aircraft;
import com.airline.reservation.exception.DuplicateResourceException;
import com.airline.reservation.exception.ResourceNotFoundException;
import com.airline.reservation.mapper.AircraftMapper;
import com.airline.reservation.repository.AircraftRepository;
import com.airline.reservation.service.AircraftService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AircraftMapper aircraftMapper;

    @Override
    public AircraftResponse createAircraft(AircraftRequest request) {
        if (aircraftRepository.existsByRegistrationNumber(request.getRegistrationNumber())) {
            throw new DuplicateResourceException("Aircraft with registration number already exists: " + request.getRegistrationNumber());
        }
        return aircraftMapper.toResponse(aircraftRepository.save(aircraftMapper.toEntity(request)));
    }

    @Override
    @Transactional(readOnly = true)
    public AircraftResponse getAircraftById(Long id) {
        return aircraftMapper.toResponse(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AircraftResponse> getAllAircraft() {
        return aircraftRepository.findAll().stream().map(aircraftMapper::toResponse).toList();
    }

    @Override
    public AircraftResponse updateAircraft(Long id, AircraftRequest request) {
        Aircraft aircraft = findById(id);
        if (!aircraft.getRegistrationNumber().equals(request.getRegistrationNumber())
                && aircraftRepository.existsByRegistrationNumber(request.getRegistrationNumber())) {
            throw new DuplicateResourceException("Aircraft with registration number already exists: " + request.getRegistrationNumber());
        }
        aircraft.setRegistrationNumber(request.getRegistrationNumber());
        aircraft.setModel(request.getModel());
        aircraft.setManufacturer(request.getManufacturer());
        aircraft.setTotalSeats(request.getTotalSeats());
        aircraft.setEconomySeats(request.getEconomySeats());
        aircraft.setBusinessSeats(request.getBusinessSeats());
        aircraft.setFirstClassSeats(request.getFirstClassSeats());
        return aircraftMapper.toResponse(aircraftRepository.save(aircraft));
    }

    @Override
    public void deleteAircraft(Long id) {
        findById(id);
        aircraftRepository.deleteById(id);
    }

    private Aircraft findById(Long id) {
        return aircraftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found with id: " + id));
    }
}
