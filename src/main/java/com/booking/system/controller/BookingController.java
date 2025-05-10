package com.booking.system.controller;

import com.booking.system.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {

    private final Logger log = LoggerFactory.getLogger(BookingController.class);

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/api/user/book")
    public ResponseEntity<String> bookClass(@AuthenticationPrincipal UserDetails userDetails,
                                            @RequestParam Long classId) {
        log.info("Request to book class with id {}", classId);
        return ResponseEntity.ok(bookingService.bookClass(userDetails.getUsername(), classId));
    }

    @DeleteMapping("/api/user/cancel-book")
    public ResponseEntity<String> cancelBook(@AuthenticationPrincipal UserDetails userDetails,
                                            @RequestParam Long bookingId) {
        log.info("Request to cancel book class with book id {}", bookingId);
        return ResponseEntity.ok(bookingService.cancelBooking(userDetails.getUsername(), bookingId));
    }

    @PostMapping("/api/user/check-in")
    public ResponseEntity<String> checkInClass(@AuthenticationPrincipal UserDetails userDetails,
                                            @RequestParam Long bookingId) {
        log.info("Request to check in class with book id {}", bookingId);
        return ResponseEntity.ok(bookingService.checkInToClass(userDetails.getUsername(), bookingId));
    }
}
