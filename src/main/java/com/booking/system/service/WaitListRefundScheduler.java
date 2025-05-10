package com.booking.system.service;

import com.booking.system.entity.*;
import com.booking.system.repository.BookingRepository;
import com.booking.system.repository.ClassScheduleRepository;
import com.booking.system.repository.UserPackageRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WaitListRefundScheduler {

    private final ClassScheduleRepository classScheduleRepository;

    private final BookingRepository bookingRepository;

    private final UserPackageRepository userPackageRepository;

    public WaitListRefundScheduler(ClassScheduleRepository classScheduleRepository, BookingRepository bookingRepository, UserPackageRepository userPackageRepository) {
        this.classScheduleRepository = classScheduleRepository;
        this.bookingRepository = bookingRepository;
        this.userPackageRepository = userPackageRepository;
    }

    @Scheduled(fixedRate = 3600000)
    public void refundWaitListedCredits() {

        LocalDateTime now = LocalDateTime.now();

        // Find classes that have ended recently (within past 6 hours to avoid repeat)
        List<ClassSchedule> endedClasses = classScheduleRepository.findEndedClasses(now.minusHours(6), now);

        for (ClassSchedule schedule : endedClasses) {
            List<Booking> waitListed = bookingRepository.findByClassScheduleIdAndStatus(
                    schedule.getId(), BookingStatus.WAIT_LISTED
            );

            for (Booking booking : waitListed) {
                User user = booking.getUser();

                UserPackage userPackage = userPackageRepository.findLatestByUserAndCountry(
                        user.getId(), schedule.getCountry().getCode()
                );

                if (userPackage != null) {
                    userPackage.setRemainingCredits(
                            userPackage.getRemainingCredits() + schedule.getRequiredCredits()
                    );
                    userPackageRepository.save(userPackage);
                }

                booking.setStatus(BookingStatus.CANCELED);
                bookingRepository.save(booking);
            }
        }
    }
}
