package com.airline.reservation.controller;

import com.airline.reservation.dto.request.AircraftRequest;
import com.airline.reservation.dto.response.AircraftResponse;
import com.airline.reservation.service.AircraftService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aircraft")
@RequiredArgsConstructor
public class AircraftController {

    private final AircraftService aircraftService;

    @PostMapping
    public ResponseEntity<AircraftResponse> createAircraft(@Valid @RequestBody AircraftRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(aircraftService.createAircraft(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AircraftResponse> getAircraftById(@PathVariable Long id) {
        return ResponseEntity.ok(aircraftService.getAircraftById(id));
    }

    @GetMapping
    public ResponseEntity<List<AircraftResponse>> getAllAircraft() {
        return ResponseEntity.ok(aircraftService.getAllAircraft());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AircraftResponse> updateAircraft(@PathVariable Long id, @Valid @RequestBody AircraftRequest request) {
        return ResponseEntity.ok(aircraftService.updateAircraft(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAircraft(@PathVariable Long id) {
        aircraftService.deleteAircraft(id);
        return ResponseEntity.noContent().build();
    }
}
