package com.airline.reservation.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingRequest {

    @NotNull(message = "User ID is required")
    @Positive
    private Long userId;

    @NotNull(message = "Flight ID is required")
    @Positive
    private Long flightId;

    @NotEmpty(message = "At least one passenger ID is required")
    private List<Long> passengerIds;

    @NotEmpty(message = "At least one seat ID is required")
    private List<Long> seatIds;
}
