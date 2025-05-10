package com.booking.system.controller;

import com.booking.system.dto.UserDTO;
import com.booking.system.service.PackageService;
import com.booking.system.service.UserPackageService;
import com.booking.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class PackageController {

    private final Logger log = LoggerFactory.getLogger(PackageController.class);

    private final PackageService packageService;

    private final UserService userService;

    private final UserPackageService userPackageService;

    public PackageController(PackageService packageService, UserService userService, UserPackageService userPackageService) {
        this.packageService = packageService;
        this.userService = userService;
        this.userPackageService = userPackageService;
    }

    @GetMapping("/api/user/packages")
    public ResponseEntity<?> getPackagesByCountry(@RequestParam String countryCode) {
        log.info("Request to get available packages with country code {}", countryCode);
        return ResponseEntity.ok(packageService.getAvailablePackages(countryCode));
    }

    @GetMapping("/api/user/my-packages")
    public ResponseEntity<?> getMyPackages(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("Request to get own packages.");
        Optional<UserDTO> userDTO = userService.findByEmail(userDetails.getUsername());
        if (userDTO.isEmpty()) {
            return ResponseEntity.ok("User not found.Please login first!");
        }
        return ResponseEntity.ok(userPackageService.getUserPackages(userDTO.get().getId()));
    }
}
