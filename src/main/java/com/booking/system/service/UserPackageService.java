package com.booking.system.service;

import com.booking.system.dto.UserPackageResponse;

import java.util.List;

public interface UserPackageService {

    public List<UserPackageResponse> getUserPackages(Long userId);
}
