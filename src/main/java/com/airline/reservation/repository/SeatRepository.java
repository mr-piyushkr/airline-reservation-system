package com.airline.reservation.repository;

import com.airline.reservation.entity.Seat;
import com.airline.reservation.enums.SeatClass;
import com.airline.reservation.enums.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByFlightId(Long flightId);
    List<Seat> findByFlightIdAndStatus(Long flightId, SeatStatus status);
    List<Seat> findByFlightIdAndSeatClass(Long flightId, SeatClass seatClass);
    Optional<Seat> findByFlightIdAndSeatNumber(Long flightId, String seatNumber);
    List<Seat> findAllByIdInAndFlightId(List<Long> ids, Long flightId);
}
