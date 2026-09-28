package com.airline.reservation.service.impl;

import com.airline.reservation.audit.AuditService;
import com.airline.reservation.dto.request.LoginRequest;
import com.airline.reservation.dto.request.RefreshTokenRequest;
import com.airline.reservation.dto.request.RegisterRequest;
import com.airline.reservation.dto.response.AuthResponse;
import com.airline.reservation.entity.RefreshToken;
import com.airline.reservation.entity.User;
import com.airline.reservation.enums.UserRole;
import com.airline.reservation.exception.BadRequestException;
import com.airline.reservation.exception.DuplicateResourceException;
import com.airline.reservation.exception.ResourceNotFoundException;
import com.airline.reservation.repository.RefreshTokenRepository;
import com.airline.reservation.repository.UserRepository;
import com.airline.reservation.security.JwtUtil;
import com.airline.reservation.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuditService auditService;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already in use");
        }
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException("Phone already in use");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(UserRole.USER)
                .active(true)
                .build();
        user = userRepository.save(user);

        auditService.log("USER_REGISTERED", "USER", String.valueOf(user.getId()),
                user.getId(), user.getEmail(), "New user registered", true,
                Map.of("firstName", user.getFirstName(), "lastName", user.getLastName()));

        return buildAuthResponse(user);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            auditService.log("USER_LOGIN_FAILED", "USER", null,
                    null, request.getEmail(), "Login failed: invalid credentials", false, null);
            throw new BadRequestException("Invalid email or password");
        }

        if (!user.isActive()) {
            throw new BadRequestException("Account is inactive");
        }

        // Revoke old refresh tokens
        refreshTokenRepository.revokeAllByUserId(user.getId());

        auditService.log("USER_LOGIN_SUCCESS", "USER", String.valueOf(user.getId()),
                user.getId(), user.getEmail(), "User logged in", true, null);

        return buildAuthResponse(user);
    }

    @Override
    public AuthResponse refresh(RefreshTokenRequest request) {
        RefreshToken stored = refreshTokenRepository.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new BadRequestException("Invalid refresh token"));

        if (stored.isRevoked()) {
            throw new BadRequestException("Refresh token has been revoked");
        }
        if (stored.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Refresh token has expired");
        }

        // Revoke used token and issue new pair
        stored.setRevoked(true);
        refreshTokenRepository.save(stored);

        User user = stored.getUser();
        return buildAuthResponse(user);
    }

    @Override
    public void logout(RefreshTokenRequest request) {
        refreshTokenRepository.findByToken(request.getRefreshToken())
                .ifPresent(token -> {
                    token.setRevoked(true);
                    refreshTokenRepository.save(token);
                    auditService.log("USER_LOGOUT", "USER", String.valueOf(token.getUser().getId()),
                            token.getUser().getId(), token.getUser().getEmail(), "User logged out", true, null);
                });
    }

    private AuthResponse buildAuthResponse(User user) {
        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getEmail(), user.getRole().name());

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiresAt(LocalDateTime.now().plusSeconds(refreshTokenExpiration / 1000))
                .revoked(false)
                .build();
        refreshTokenRepository.save(refreshToken);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .expiresIn(accessTokenExpiration / 1000)
                .userId(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().name())
                .build();
    }
}
