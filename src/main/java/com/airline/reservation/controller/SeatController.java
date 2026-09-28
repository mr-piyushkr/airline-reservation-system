package com.airline.reservation.controller;

import com.airline.reservation.dto.request.SeatRequest;
import com.airline.reservation.dto.response.SeatResponse;
import com.airline.reservation.enums.SeatClass;
import com.airline.reservation.service.SeatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @PostMapping
    public ResponseEntity<SeatResponse> createSeat(@Valid @RequestBody SeatRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(seatService.createSeat(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatResponse> getSeatById(@PathVariable Long id) {
        return ResponseEntity.ok(seatService.getSeatById(id));
    }

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<SeatResponse>> getSeatsByFlight(@PathVariable Long flightId) {
        return ResponseEntity.ok(seatService.getSeatsByFlight(flightId));
    }

    @GetMapping("/flight/{flightId}/available")
    public ResponseEntity<List<SeatResponse>> getAvailableSeats(@PathVariable Long flightId) {
        return ResponseEntity.ok(seatService.getAvailableSeatsByFlight(flightId));
    }

    @GetMapping("/flight/{flightId}/class/{seatClass}")
    public ResponseEntity<List<SeatResponse>> getSeatsByClass(
            @PathVariable Long flightId,
            @PathVariable SeatClass seatClass) {
        return ResponseEntity.ok(seatService.getSeatsByFlightAndClass(flightId, seatClass));
    }
}
