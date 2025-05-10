package com.booking.system.serviceImpl;

import com.booking.system.entity.ClassSchedule;
import com.booking.system.repository.ClassScheduleRepository;
import com.booking.system.service.ClassScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassScheduleServiceImpl implements ClassScheduleService {

    private final ClassScheduleRepository classScheduleRepository;

    public ClassScheduleServiceImpl(ClassScheduleRepository classScheduleRepository) {
        this.classScheduleRepository = classScheduleRepository;
    }

    @Override
    public List<ClassSchedule> getAvailableSchedules(String countryCode) {
        return classScheduleRepository.findAvailableSchedulesByCountry(countryCode);
    }
}
