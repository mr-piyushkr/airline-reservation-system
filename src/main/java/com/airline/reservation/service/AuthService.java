package com.airline.reservation.service;

import com.airline.reservation.dto.request.LoginRequest;
import com.airline.reservation.dto.request.RefreshTokenRequest;
import com.airline.reservation.dto.request.RegisterRequest;
import com.airline.reservation.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    AuthResponse refresh(RefreshTokenRequest request);
    void logout(RefreshTokenRequest request);
}
