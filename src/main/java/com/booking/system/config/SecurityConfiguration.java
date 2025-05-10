package com.booking.system.config;

import com.booking.system.security.UserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final PasswordEncoder passwordEncoder;

    public SecurityConfiguration(
        UserDetailsService userDetailsService,
        @Lazy JwtAuthenticationFilter jwtAuthenticationFilter,
        @Lazy PasswordEncoder passwordEncoder
    ) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public AuthenticationManager authenticationManager() {

        DaoAuthenticationProvider userProvider = new DaoAuthenticationProvider();
        userProvider.setUserDetailsService(userDetailsService);
        userProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(userProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/user/authenticated", "/api/user-register").permitAll()
                .requestMatchers("/api/user/**").hasRole("USER")
                .anyRequest().authenticated()
            )
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
