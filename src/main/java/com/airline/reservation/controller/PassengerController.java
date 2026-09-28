package com.airline.reservation.controller;

import com.airline.reservation.dto.request.PassengerRequest;
import com.airline.reservation.dto.response.PassengerResponse;
import com.airline.reservation.service.PassengerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @PostMapping
    public ResponseEntity<PassengerResponse> createPassenger(@Valid @RequestBody PassengerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(passengerService.createPassenger(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponse> getPassengerById(@PathVariable Long id) {
        return ResponseEntity.ok(passengerService.getPassengerById(id));
    }

    @GetMapping("/passport/{passportNumber}")
    public ResponseEntity<PassengerResponse> getPassengerByPassport(@PathVariable String passportNumber) {
        return ResponseEntity.ok(passengerService.getPassengerByPassportNumber(passportNumber));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PassengerResponse>> getPassengersByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(passengerService.getPassengersByUser(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassengerResponse> updatePassenger(@PathVariable Long id, @Valid @RequestBody PassengerRequest request) {
        return ResponseEntity.ok(passengerService.updatePassenger(id, request));
    }
}
