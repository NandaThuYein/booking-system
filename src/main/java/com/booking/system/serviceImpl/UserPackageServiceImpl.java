package com.booking.system.serviceImpl;

import com.booking.system.dto.UserPackageResponse;
import com.booking.system.entity.UserPackage;
import com.booking.system.repository.UserPackageRepository;
import com.booking.system.service.UserPackageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserPackageServiceImpl implements UserPackageService {

    private final UserPackageRepository userPackageRepository;

    public UserPackageServiceImpl(UserPackageRepository userPackageRepository) {
        this.userPackageRepository = userPackageRepository;
    }

    @Override
    public List<UserPackageResponse> getUserPackages(Long userId) {
        List<UserPackage> userPackages = userPackageRepository.findByUserId(userId);

        return userPackages.stream().map(up -> {
            UserPackageResponse dto = new UserPackageResponse();
            dto.setId(up.getId());
            dto.setPackageName(up.getaPackage().getName());
            dto.setCountryCode(up.getaPackage().getCountry().getCode());
            dto.setRemainingCredits(up.getRemainingCredits());
            dto.setExpiryDate(up.getExpiryDate());
            dto.setExpired(up.isExpired());
            return dto;
        }).collect(Collectors.toList());
    }
}
