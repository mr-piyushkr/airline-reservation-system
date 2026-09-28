package com.airline.reservation.mapper;

import com.airline.reservation.dto.response.BookingResponse;
import com.airline.reservation.dto.response.BookingSummaryResponse;
import com.airline.reservation.entity.Booking;
import com.airline.reservation.entity.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingMapper {

    private final PassengerMapper passengerMapper;
    private final SeatMapper seatMapper;

    public BookingResponse toResponse(Booking booking) {
        Payment payment = booking.getPayment();
        return BookingResponse.builder()
                .id(booking.getId())
                .bookingReference(booking.getBookingReference())
                .status(booking.getStatus())
                .userId(booking.getUser().getId())
                .userEmail(booking.getUser().getEmail())
                .flightId(booking.getFlight().getId())
                .flightNumber(booking.getFlight().getFlightNumber())
                .originIata(booking.getFlight().getOriginAirport().getIataCode())
                .originCity(booking.getFlight().getOriginAirport().getCity())
                .destinationIata(booking.getFlight().getDestinationAirport().getIataCode())
                .destinationCity(booking.getFlight().getDestinationAirport().getCity())
                .departureTime(booking.getFlight().getDepartureTime())
                .arrivalTime(booking.getFlight().getArrivalTime())
                .passengers(booking.getPassengers().stream().map(passengerMapper::toResponse).toList())
                .seats(booking.getSeats().stream().map(seatMapper::toResponse).toList())
                .totalAmount(booking.getTotalAmount())
                .paymentStatus(payment != null ? payment.getStatus() : null)
                .transactionId(payment != null ? payment.getTransactionId() : null)
                .paymentMethod(payment != null ? payment.getPaymentMethod() : null)
                .createdAt(booking.getCreatedAt())
                .updatedAt(booking.getUpdatedAt())
                .build();
    }

    public BookingSummaryResponse toSummaryResponse(Booking booking) {
        return BookingSummaryResponse.builder()
                .id(booking.getId())
                .bookingReference(booking.getBookingReference())
                .status(booking.getStatus())
                .flightNumber(booking.getFlight().getFlightNumber())
                .originIata(booking.getFlight().getOriginAirport().getIataCode())
                .destinationIata(booking.getFlight().getDestinationAirport().getIataCode())
                .departureTime(booking.getFlight().getDepartureTime())
                .totalAmount(booking.getTotalAmount())
                .createdAt(booking.getCreatedAt())
                .build();
    }
}
