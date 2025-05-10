package com.booking.system.serviceImpl;

import com.booking.system.dto.UserDTO;
import com.booking.system.dto.UserProfile;
import com.booking.system.entity.Country;
import com.booking.system.entity.User;
import com.booking.system.mapper.UserMapper;
import com.booking.system.repository.CountryRepository;
import com.booking.system.repository.UserRepository;
import com.booking.system.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final CountryRepository countryRepository;

    private final UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserServiceImpl(UserRepository userRepository, CountryRepository countryRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.countryRepository = countryRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toDto);
    }

    @Override
    public String userRegister(String email, String password, String name, String countryCode) {
        if (userRepository.existsByEmail(email)) {
            return "Email is already registered.";
        }

        Optional<Country> country = countryRepository.findByCode(countryCode);
        if (country.isEmpty()) {
            return "Invalid country code.";
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setCountry(country.get());
        user.setVerified(false);

        userRepository.save(user);

        boolean emailSent = sendVerifyEmail(email); // mock
        if (!emailSent) {
            return "Failed to send verification email.";
        }

        return "Registration successful. Please verify your email.";
    }

    @Override
    public ResponseEntity<?> userProfile() {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByEmail(currentEmail);
        if (user.isPresent()) {
            User user1 = user.get();
            return ResponseEntity.ok(new UserProfile(user1.getEmail(), user1.getName(), user1.getCountry().getName()));
        }
        return ResponseEntity.ok("User not found.");
    }

    private boolean sendVerifyEmail(String email) {
        return true;
    }
}
