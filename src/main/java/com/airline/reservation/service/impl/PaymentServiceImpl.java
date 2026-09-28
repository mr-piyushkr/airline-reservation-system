package com.airline.reservation.service.impl;

import com.airline.reservation.audit.AuditService;
import com.airline.reservation.dto.request.PaymentRequest;
import com.airline.reservation.dto.response.PaymentResponse;
import com.airline.reservation.entity.Booking;
import com.airline.reservation.entity.Payment;
import com.airline.reservation.entity.Seat;
import com.airline.reservation.enums.BookingStatus;
import com.airline.reservation.enums.PaymentStatus;
import com.airline.reservation.enums.SeatStatus;
import com.airline.reservation.exception.BadRequestException;
import com.airline.reservation.exception.ResourceNotFoundException;
import com.airline.reservation.mapper.PaymentMapper;
import com.airline.reservation.repository.BookingRepository;
import com.airline.reservation.repository.PaymentRepository;
import com.airline.reservation.repository.SeatRepository;
import com.airline.reservation.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final PaymentMapper paymentMapper;
    private final AuditService auditService;

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + request.getBookingId()));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BadRequestException("Cannot process payment for a cancelled booking: " + booking.getBookingReference());
        }
        if (booking.getStatus() == BookingStatus.CONFIRMED) {
            throw new BadRequestException("Booking is already confirmed and paid: " + booking.getBookingReference());
        }

        Payment payment = paymentRepository.findByBookingId(booking.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Payment record not found for booking: " + booking.getBookingReference()));

        if (payment.getStatus() == PaymentStatus.SUCCESS) {
            throw new BadRequestException("Payment already processed successfully for booking: " + booking.getBookingReference());
        }

        String transactionId = "TXN-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase(Locale.ROOT);
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setTransactionId(transactionId);

        if (request.isSimulateSuccess()) {
            payment.setStatus(PaymentStatus.SUCCESS);
            booking.setStatus(BookingStatus.CONFIRMED);
            for (Seat seat : booking.getSeats()) {
                seat.setStatus(SeatStatus.BOOKED);
            }
            seatRepository.saveAll(booking.getSeats());
            bookingRepository.save(booking);

            auditService.log("PAYMENT_SUCCESS", "PAYMENT", String.valueOf(payment.getId()),
                    booking.getUser().getId(), booking.getUser().getEmail(),
                    "Payment successful for booking: " + booking.getBookingReference(), true,
                    Map.of("transactionId", transactionId, "amount", payment.getAmount().toString()));

            auditService.log("BOOKING_CONFIRMED", "BOOKING", String.valueOf(booking.getId()),
                    booking.getUser().getId(), booking.getUser().getEmail(),
                    "Booking confirmed: " + booking.getBookingReference(), true,
                    Map.of("bookingReference", booking.getBookingReference()));
        } else {
            payment.setStatus(PaymentStatus.FAILED);

            auditService.log("PAYMENT_FAILED", "PAYMENT", String.valueOf(payment.getId()),
                    booking.getUser().getId(), booking.getUser().getEmail(),
                    "Payment failed for booking: " + booking.getBookingReference(), false,
                    Map.of("bookingReference", booking.getBookingReference()));
        }

        return paymentMapper.toResponse(paymentRepository.save(payment));
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentByBookingId(Long bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            throw new ResourceNotFoundException("Booking not found with id: " + bookingId);
        }
        Payment payment = paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for booking id: " + bookingId));
        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getBookingUserId(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .map(b -> b.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
    }
}
