package com.airline.reservation.controller;

import com.airline.reservation.dto.request.UpdateProfileRequest;
import com.airline.reservation.dto.response.UserResponse;
import com.airline.reservation.security.AuthenticatedUser;
import com.airline.reservation.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyProfile(@AuthenticationPrincipal AuthenticatedUser principal) {
        return ResponseEntity.ok(profileService.getMyProfile(principal.getId()));
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateMyProfile(
            @AuthenticationPrincipal AuthenticatedUser principal,
            @Valid @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(profileService.updateMyProfile(principal.getId(), request));
    }
}
