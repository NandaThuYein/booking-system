package com.booking.system.controller;

import com.booking.system.dto.UserDTO;
import com.booking.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/user-register")
    public ResponseEntity<?> userRegister(
        @RequestParam String email,
        @RequestParam String password,
        @RequestParam String name,
        @RequestParam String countryCode
    ) {
        log.info("Request to user registration with email {}", email);
        return ResponseEntity.ok(userService.userRegister(email, password, name, countryCode));
    }

    @PostMapping("/api/user/profile")
    public ResponseEntity<?> userProfile() {
        log.info("Request to get user profile.");
        return ResponseEntity.ok(userService.userProfile());
    }

    @PostMapping("/api/user/change-password")
    public ResponseEntity<?> changePassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam String oldPassword,
            @RequestParam String newPassword
    ) {
        log.info("Request to change password.");
        return ResponseEntity.ok(userService.changePassword(userDetails.getUsername(), oldPassword, newPassword));
    }

    @PostMapping("/api/request-reset")
    public ResponseEntity<?> userRegister(
            @RequestParam String email
    ) {
        log.info("Request to reset password token with email {}", email);
        return ResponseEntity.ok(userService.requestResetPassword(email));
    }

    @PostMapping("/api/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword
    ) {
        log.info("Request to reset password with token {}", token);
        return ResponseEntity.ok(userService.resetPassword(token, newPassword));
    }
}
