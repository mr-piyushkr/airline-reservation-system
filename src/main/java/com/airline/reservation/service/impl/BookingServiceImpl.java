package com.airline.reservation.service.impl;

import com.airline.reservation.audit.AuditService;
import com.airline.reservation.dto.request.BookingRequest;
import com.airline.reservation.dto.response.BookingResponse;
import com.airline.reservation.dto.response.BookingSummaryResponse;
import com.airline.reservation.entity.*;
import com.airline.reservation.enums.BookingStatus;
import com.airline.reservation.enums.FlightStatus;
import com.airline.reservation.enums.PaymentStatus;
import com.airline.reservation.enums.SeatStatus;
import com.airline.reservation.exception.BadRequestException;
import com.airline.reservation.exception.ResourceNotFoundException;
import com.airline.reservation.mapper.BookingMapper;
import com.airline.reservation.repository.*;
import com.airline.reservation.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;
    private final SeatRepository seatRepository;
    private final PaymentRepository paymentRepository;
    private final BookingMapper bookingMapper;
    private final AuditService auditService;

    @Override
    public BookingResponse createBooking(BookingRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        Flight flight = flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + request.getFlightId()));

        if (flight.getStatus() == FlightStatus.CANCELLED) {
            throw new BadRequestException("Cannot book a cancelled flight: " + flight.getFlightNumber());
        }
        if (flight.getStatus() == FlightStatus.DEPARTED || flight.getStatus() == FlightStatus.ARRIVED) {
            throw new BadRequestException("Cannot book flight that has already departed or arrived: " + flight.getFlightNumber());
        }

        List<Long> passengerIds = request.getPassengerIds();
        List<Passenger> passengers = passengerRepository.findAllById(passengerIds);
        if (passengers.size() != passengerIds.size()) {
            throw new ResourceNotFoundException("One or more passengers not found");
        }

        List<Long> seatIds = request.getSeatIds();
        List<Seat> seats = seatRepository.findAllByIdInAndFlightId(seatIds, flight.getId());
        if (seats.size() != seatIds.size()) {
            throw new BadRequestException("One or more seats do not belong to flight: " + flight.getFlightNumber());
        }

        if (seats.size() != passengers.size()) {
            throw new BadRequestException("Number of seats must match number of passengers");
        }

        List<Seat> unavailable = seats.stream()
                .filter(s -> s.getStatus() != SeatStatus.AVAILABLE)
                .toList();
        if (!unavailable.isEmpty()) {
            String nums = unavailable.stream().map(Seat::getSeatNumber).reduce((a, b) -> a + ", " + b).orElse("");
            throw new BadRequestException("Seats are not available: " + nums);
        }

        BigDecimal totalAmount = seats.stream()
                .map(Seat::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String bookingReference = generateUniqueBookingReference();

        Booking booking = Booking.builder()
                .bookingReference(bookingReference)
                .user(user)
                .flight(flight)
                .passengers(passengers)
                .seats(seats)
                .status(BookingStatus.PENDING)
                .totalAmount(totalAmount)
                .build();
        booking = bookingRepository.save(booking);

        Payment payment = Payment.builder()
                .booking(booking)
                .amount(totalAmount)
                .status(PaymentStatus.PENDING)
                .build();
        paymentRepository.save(payment);
        booking.setPayment(payment);

        auditService.log("BOOKING_CREATED", "BOOKING", String.valueOf(booking.getId()),
                user.getId(), user.getEmail(),
                "Booking created: " + bookingReference, true,
                Map.of("flightNumber", flight.getFlightNumber(), "totalAmount", totalAmount.toString()));

        return bookingMapper.toResponse(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingResponse getBookingById(Long id) {
        return bookingMapper.toResponse(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public BookingResponse getBookingByReference(String bookingReference) {
        Booking booking = bookingRepository.findByBookingReference(bookingReference)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with reference: " + bookingReference));
        return bookingMapper.toResponse(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingSummaryResponse> getBookingsByUser(Long userId, BookingStatus status) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        List<Booking> bookings = (status != null)
                ? bookingRepository.findByUserIdAndStatusOrderByCreatedAtDesc(userId, status)
                : bookingRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return bookings.stream().map(bookingMapper::toSummaryResponse).toList();
    }

    @Override
    public BookingResponse cancelBooking(Long id) {
        Booking booking = findById(id);

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BadRequestException("Booking is already cancelled: " + booking.getBookingReference());
        }

        booking.getSeats().forEach(seat -> seat.setStatus(SeatStatus.AVAILABLE));
        seatRepository.saveAll(booking.getSeats());

        booking.setStatus(BookingStatus.CANCELLED);

        Payment payment = booking.getPayment();
        if (payment != null) {
            payment.setStatus(payment.getStatus() == PaymentStatus.SUCCESS
                    ? PaymentStatus.REFUNDED
                    : PaymentStatus.FAILED);
            paymentRepository.save(payment);
        }

        Booking saved = bookingRepository.save(booking);

        auditService.log("BOOKING_CANCELLED", "BOOKING", String.valueOf(id),
                booking.getUser().getId(), booking.getUser().getEmail(),
                "Booking cancelled: " + booking.getBookingReference(), true,
                Map.of("bookingReference", booking.getBookingReference()));

        return bookingMapper.toResponse(saved);
    }

    // Ownership check: verify booking belongs to given userId
    @Override
    @Transactional(readOnly = true)
    public boolean isOwner(Long bookingId, Long userId) {
        return bookingRepository.findById(bookingId)
                .map(b -> b.getUser().getId().equals(userId))
                .orElse(false);
    }

    private Booking findById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
    }

    private String generateUniqueBookingReference() {
        String ref;
        do {
            ref = "PNR-" + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase(java.util.Locale.ROOT);
        } while (bookingRepository.existsByBookingReference(ref));
        return ref;
    }
}
