package com.airline.reservation.service.impl;

import com.airline.reservation.audit.AuditService;
import com.airline.reservation.dto.request.UpdateProfileRequest;
import com.airline.reservation.dto.response.UserResponse;
import com.airline.reservation.entity.User;
import com.airline.reservation.exception.BadRequestException;
import com.airline.reservation.exception.DuplicateResourceException;
import com.airline.reservation.exception.ResourceNotFoundException;
import com.airline.reservation.mapper.UserMapper;
import com.airline.reservation.repository.UserRepository;
import com.airline.reservation.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuditService auditService;

    @Override
    @Transactional(readOnly = true)
    public UserResponse getMyProfile(Long userId) {
        return userMapper.toResponse(findById(userId));
    }

    @Override
    public UserResponse updateMyProfile(Long userId, UpdateProfileRequest request) {
        User user = findById(userId);

        if (!user.getPhone().equals(request.getPhone()) && userRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException("Phone already in use");
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());

        // Optional password change
        if (request.getNewPassword() != null && !request.getNewPassword().isBlank()) {
            if (request.getCurrentPassword() == null || request.getCurrentPassword().isBlank()) {
                throw new BadRequestException("Current password is required to set a new password");
            }
            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                throw new BadRequestException("Current password is incorrect");
            }
            if (request.getNewPassword().length() < 8) {
                throw new BadRequestException("New password must be at least 8 characters");
            }
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        }

        User saved = userRepository.save(user);
        auditService.log("USER_PROFILE_UPDATED", "USER", String.valueOf(userId),
                userId, user.getEmail(), "Profile updated", true,
                Map.of("firstName", saved.getFirstName(), "lastName", saved.getLastName()));
        return userMapper.toResponse(saved);
    }

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
}
