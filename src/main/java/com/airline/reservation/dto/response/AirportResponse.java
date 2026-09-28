package com.airline.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AirportResponse {
    private Long id;
    private String iataCode;
    private String name;
    private String city;
    private String country;
    private String timezone;
}
