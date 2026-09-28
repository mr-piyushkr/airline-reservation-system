package com.airline.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class PassengerResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String passportNumber;
    private LocalDate dateOfBirth;
    private String nationality;
    private Long userId;
    private LocalDateTime createdAt;
}
