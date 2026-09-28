package com.airline.reservation.mapper;

import com.airline.reservation.dto.request.SeatRequest;
import com.airline.reservation.dto.response.SeatResponse;
import com.airline.reservation.entity.Flight;
import com.airline.reservation.entity.Seat;
import org.springframework.stereotype.Component;

@Component
public class SeatMapper {

    public Seat toEntity(SeatRequest request, Flight flight) {
        return Seat.builder()
                .flight(flight)
                .seatNumber(request.getSeatNumber())
                .seatClass(request.getSeatClass())
                .status(request.getStatus())
                .price(request.getPrice())
                .build();
    }

    public SeatResponse toResponse(Seat seat) {
        return SeatResponse.builder()
                .id(seat.getId())
                .flightId(seat.getFlight().getId())
                .seatNumber(seat.getSeatNumber())
                .seatClass(seat.getSeatClass())
                .status(seat.getStatus())
                .price(seat.getPrice())
                .build();
    }
}
