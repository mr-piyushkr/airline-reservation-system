package com.airline.reservation.dto.request;

import com.airline.reservation.enums.SeatClass;
import com.airline.reservation.enums.SeatStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SeatRequest {

    @NotNull(message = "Flight ID is required")
    @Positive
    private Long flightId;

    @NotBlank(message = "Seat number is required")
    @Size(max = 5)
    private String seatNumber;

    @NotNull(message = "Seat class is required")
    private SeatClass seatClass;

    @NotNull(message = "Seat status is required")
    private SeatStatus status;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;
}
