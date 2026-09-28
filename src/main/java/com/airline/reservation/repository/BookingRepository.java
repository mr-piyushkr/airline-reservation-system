package com.airline.reservation.repository;

import com.airline.reservation.entity.Booking;
import com.airline.reservation.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByBookingReference(String bookingReference);
    List<Booking> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<Booking> findByUserIdAndStatusOrderByCreatedAtDesc(Long userId, BookingStatus status);
    boolean existsByBookingReference(String bookingReference);
}
