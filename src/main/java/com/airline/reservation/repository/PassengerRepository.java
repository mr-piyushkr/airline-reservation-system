package com.airline.reservation.repository;

import com.airline.reservation.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    Optional<Passenger> findByPassportNumber(String passportNumber);
    boolean existsByPassportNumber(String passportNumber);
    List<Passenger> findByUserId(Long userId);
}
