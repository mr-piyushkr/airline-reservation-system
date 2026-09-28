package com.airline.reservation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AirportRequest {

    @NotBlank(message = "IATA code is required")
    @Size(min = 3, max = 3, message = "IATA code must be exactly 3 characters")
    @Pattern(regexp = "^[A-Z]{3}$", message = "IATA code must be 3 uppercase letters")
    private String iataCode;

    @NotBlank(message = "Airport name is required")
    private String name;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "Timezone is required")
    private String timezone;
}
