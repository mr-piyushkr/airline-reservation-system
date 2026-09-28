package com.airline.reservation.repository;

import com.airline.reservation.entity.Flight;
import com.airline.reservation.enums.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    Optional<Flight> findByFlightNumber(String flightNumber);
    boolean existsByFlightNumber(String flightNumber);

    @Query("SELECT f FROM Flight f WHERE f.originAirport.iataCode = :origin " +
           "AND f.destinationAirport.iataCode = :destination " +
           "AND f.departureTime >= :from AND f.departureTime <= :to " +
           "AND f.status = 'SCHEDULED'")
    List<Flight> searchFlights(@Param("origin") String origin,
                               @Param("destination") String destination,
                               @Param("from") LocalDateTime from,
                               @Param("to") LocalDateTime to);

    List<Flight> findByStatus(FlightStatus status);
}
