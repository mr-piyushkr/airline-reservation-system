package com.airline.reservation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AircraftRequest {

    @NotBlank(message = "Registration number is required")
    private String registrationNumber;

    @NotBlank(message = "Model is required")
    private String model;

    @NotBlank(message = "Manufacturer is required")
    private String manufacturer;

    @Positive(message = "Total seats must be positive")
    private int totalSeats;

    @PositiveOrZero(message = "Economy seats must be zero or positive")
    private int economySeats;

    @PositiveOrZero(message = "Business seats must be zero or positive")
    private int businessSeats;

    @PositiveOrZero(message = "First class seats must be zero or positive")
    private int firstClassSeats;
}
