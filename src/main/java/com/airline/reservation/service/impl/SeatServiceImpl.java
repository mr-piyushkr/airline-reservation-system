package com.airline.reservation.service.impl;

import com.airline.reservation.dto.request.SeatRequest;
import com.airline.reservation.dto.response.SeatResponse;
import com.airline.reservation.entity.Flight;
import com.airline.reservation.entity.Seat;
import com.airline.reservation.enums.SeatClass;
import com.airline.reservation.enums.SeatStatus;
import com.airline.reservation.exception.DuplicateResourceException;
import com.airline.reservation.exception.ResourceNotFoundException;
import com.airline.reservation.mapper.SeatMapper;
import com.airline.reservation.repository.FlightRepository;
import com.airline.reservation.repository.SeatRepository;
import com.airline.reservation.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final FlightRepository flightRepository;
    private final SeatMapper seatMapper;

    @Override
    @Transactional
    public SeatResponse createSeat(SeatRequest request) {
        Flight flight = flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + request.getFlightId()));
        if (seatRepository.findByFlightIdAndSeatNumber(flight.getId(), request.getSeatNumber()).isPresent()) {
            throw new DuplicateResourceException("Seat " + request.getSeatNumber() + " already exists for flight: " + flight.getFlightNumber());
        }
        Seat saved = seatRepository.save(seatMapper.toEntity(request, flight));
        return seatMapper.toResponse(saved);
    }

    @Override
    public List<SeatResponse> getSeatsByFlight(Long flightId) {
        return seatRepository.findByFlightId(flightId).stream().map(seatMapper::toResponse).toList();
    }

    @Override
    public List<SeatResponse> getAvailableSeatsByFlight(Long flightId) {
        return seatRepository.findByFlightIdAndStatus(flightId, SeatStatus.AVAILABLE)
                .stream().map(seatMapper::toResponse).toList();
    }

    @Override
    public List<SeatResponse> getSeatsByFlightAndClass(Long flightId, SeatClass seatClass) {
        return seatRepository.findByFlightIdAndSeatClass(flightId, seatClass)
                .stream().map(seatMapper::toResponse).toList();
    }

    @Override
    public SeatResponse getSeatById(Long id) {
        return seatMapper.toResponse(
                seatRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Seat not found with id: " + id))
        );
    }
}
