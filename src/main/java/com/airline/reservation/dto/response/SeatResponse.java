package com.airline.reservation.dto.response;

import com.airline.reservation.enums.SeatClass;
import com.airline.reservation.enums.SeatStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class SeatResponse {
    private Long id;
    private Long flightId;
    private String seatNumber;
    private SeatClass seatClass;
    private SeatStatus status;
    private BigDecimal price;
}
