package com.airline.reservation.service;

import com.airline.reservation.dto.request.UpdateProfileRequest;
import com.airline.reservation.dto.response.UserResponse;

public interface ProfileService {
    UserResponse getMyProfile(Long userId);
    UserResponse updateMyProfile(Long userId, UpdateProfileRequest request);
}
