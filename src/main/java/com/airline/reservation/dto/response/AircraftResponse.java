package com.airline.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AircraftResponse {
    private Long id;
    private String registrationNumber;
    private String model;
    private String manufacturer;
    private int totalSeats;
    private int economySeats;
    private int businessSeats;
    private int firstClassSeats;
    private boolean active;
}
