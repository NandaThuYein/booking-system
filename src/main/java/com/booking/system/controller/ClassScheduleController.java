package com.booking.system.controller;

import com.booking.system.service.ClassScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClassScheduleController {

    private final Logger log = LoggerFactory.getLogger(ClassScheduleController.class);

    private final ClassScheduleService classScheduleService;

    public ClassScheduleController(ClassScheduleService classScheduleService) {
        this.classScheduleService = classScheduleService;
    }

    @GetMapping("/api/user/class-schedules")
    public ResponseEntity<?> getSchedulesByCountry(@RequestParam String countryCode) {
        log.info("Request to get available class schedules with country code {}", countryCode);
        return ResponseEntity.ok(classScheduleService.getAvailableSchedules(countryCode));
    }
}
