package com.booking.system.service;


import com.booking.system.dto.UserDTO;
import com.booking.system.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserService {

    Optional<UserDTO> findByEmail(String email);

    public String userRegister(String email, String password, String name, String country);

    ResponseEntity<?> userProfile();

    public String changePassword(String email, String oldPassword, String newPassword);

    public String requestResetPassword(String email);

    public String resetPassword(String token, String newPassword);
}
