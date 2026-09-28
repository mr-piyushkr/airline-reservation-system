package com.airline.reservation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {

    @NotNull(message = "Booking ID is required")
    @Positive
    private Long bookingId;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    // Mock control: if true → simulate success, if false → simulate failure
    // Defaults to true (success) when not provided
    private boolean simulateSuccess = true;
}
