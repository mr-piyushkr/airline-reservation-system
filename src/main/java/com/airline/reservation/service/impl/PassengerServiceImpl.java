package com.airline.reservation.service.impl;

import com.airline.reservation.dto.request.PassengerRequest;
import com.airline.reservation.dto.response.PassengerResponse;
import com.airline.reservation.entity.Passenger;
import com.airline.reservation.entity.User;
import com.airline.reservation.exception.DuplicateResourceException;
import com.airline.reservation.exception.ResourceNotFoundException;
import com.airline.reservation.mapper.PassengerMapper;
import com.airline.reservation.repository.PassengerRepository;
import com.airline.reservation.repository.UserRepository;
import com.airline.reservation.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final UserRepository userRepository;
    private final PassengerMapper passengerMapper;

    @Override
    public PassengerResponse createPassenger(PassengerRequest request) {
        if (passengerRepository.existsByPassportNumber(request.getPassportNumber())) {
            throw new DuplicateResourceException("Passenger with passport number already exists: " + request.getPassportNumber());
        }
        User user = resolveUser(request.getUserId());
        Passenger saved = passengerRepository.save(passengerMapper.toEntity(request, user));
        return passengerMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PassengerResponse getPassengerById(Long id) {
        return passengerMapper.toResponse(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PassengerResponse getPassengerByPassportNumber(String passportNumber) {
        return passengerMapper.toResponse(
                passengerRepository.findByPassportNumber(passportNumber)
                        .orElseThrow(() -> new ResourceNotFoundException("Passenger not found with passport: " + passportNumber))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<PassengerResponse> getPassengersByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        return passengerRepository.findByUserId(userId).stream().map(passengerMapper::toResponse).toList();
    }

    @Override
    public PassengerResponse updatePassenger(Long id, PassengerRequest request) {
        Passenger passenger = findById(id);
        if (!passenger.getPassportNumber().equals(request.getPassportNumber())
                && passengerRepository.existsByPassportNumber(request.getPassportNumber())) {
            throw new DuplicateResourceException("Passenger with passport number already exists: " + request.getPassportNumber());
        }
        User user = resolveUser(request.getUserId());
        passenger.setFirstName(request.getFirstName());
        passenger.setLastName(request.getLastName());
        passenger.setPassportNumber(request.getPassportNumber());
        passenger.setDateOfBirth(request.getDateOfBirth());
        passenger.setNationality(request.getNationality());
        passenger.setUser(user);
        return passengerMapper.toResponse(passengerRepository.save(passenger));
    }

    private Passenger findById(Long id) {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found with id: " + id));
    }

    private User resolveUser(Long userId) {
        if (userId == null) return null;
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }
}
