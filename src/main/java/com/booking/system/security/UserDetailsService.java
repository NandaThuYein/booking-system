package com.booking.system.security;

import com.booking.system.dto.UserDTO;
import com.booking.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    private final UserService userService;

    public UserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.debug("Authenticating user {}", email);

        UserDTO userDTO = userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Merchant User with email " + email + " was not found in the database"));

        return User.withUsername(userDTO.getEmail())
                .password(userDTO.getPassword())
                .roles("USER")
                .build();
    }
}
