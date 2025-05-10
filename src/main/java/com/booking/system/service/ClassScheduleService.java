package com.booking.system.service;

import com.booking.system.entity.ClassSchedule;

import java.util.List;

public interface ClassScheduleService {

    public List<ClassSchedule> getAvailableSchedules(String countryCode);
}
