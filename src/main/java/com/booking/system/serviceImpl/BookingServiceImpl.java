package com.booking.system.serviceImpl;

import com.booking.system.entity.*;
import com.booking.system.entity.Package;
import com.booking.system.repository.BookingRepository;
import com.booking.system.repository.ClassScheduleRepository;
import com.booking.system.repository.UserPackageRepository;
import com.booking.system.repository.UserRepository;
import com.booking.system.service.BookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final ClassScheduleRepository classScheduleRepository;

    private final UserPackageRepository userPackageRepository;

    private final UserRepository userRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, ClassScheduleRepository classScheduleRepository, UserPackageRepository userPackageRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.classScheduleRepository = classScheduleRepository;
        this.userPackageRepository = userPackageRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public String bookClass(String email, Long classId) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ClassSchedule classSchedule = classScheduleRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        if (classSchedule.getStartTime().isBefore(LocalDateTime.now())) {
            return "Cannot book a class that already started.";
        }

        // Check for overlapping bookings
        List<Booking> overlapping = bookingRepository.findOverlappingBookings(
                user.getId(), classSchedule.getStartTime(), classSchedule.getEndTime());

        if (!overlapping.isEmpty()) {
            return "You already have a booking during this time.";
        }

        UserPackage userPackage = userPackageRepository.findValidPackage(
            user.getId(),
            classSchedule.getCountry().getCode(),
            LocalDate.now()
        );

        if (userPackage == null || userPackage.getRemainingCredits() < classSchedule.getRequiredCredits()) {
            return "Insufficient credits or no valid package.";
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setClassSchedule(classSchedule);
        booking.setBookingTime(LocalDateTime.now());
        booking.setUserPackage(userPackage);

        if (classSchedule.getMaxSlots() > 0) {
            booking.setStatus(BookingStatus.BOOKED);

            userPackage.setRemainingCredits(userPackage.getRemainingCredits() - classSchedule.getRequiredCredits());
            userPackageRepository.save(userPackage);

            classSchedule.setMaxSlots(classSchedule.getMaxSlots() - 1);
            classScheduleRepository.save(classSchedule);

            bookingRepository.save(booking);
            return "Class booked successfully.";
        } else {
            booking.setStatus(BookingStatus.WAIT_LISTED);
            bookingRepository.save(booking);
            return "Class is full. You've been added to the waitList.";
        }
    }

    @Override
    @Transactional
    public String cancelBooking(String email, Long bookingId) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getUser().getId().equals(user.getId())) {
            return "Unauthorized to cancel this booking";
        }

        if (booking.getStatus() != BookingStatus.BOOKED) {
            return "Cannot cancel: not a booked class";
        }

        ClassSchedule schedule = booking.getClassSchedule();
        LocalDateTime now = LocalDateTime.now();
        boolean refund = schedule.getStartTime().isAfter(now.plusHours(4));

        if (refund) {
            UserPackage userPackage = userPackageRepository.findLatestByUserAndCountry(
                    user.getId(), schedule.getCountry().getCode()
            );
            if (userPackage != null) {
                userPackage.setRemainingCredits(userPackage.getRemainingCredits() + schedule.getRequiredCredits());
                userPackageRepository.save(userPackage);
            }
        }

        booking.setStatus(BookingStatus.CANCELED);
        booking.setCancelTime(LocalDateTime.now());
        bookingRepository.save(booking);

        schedule.setMaxSlots(schedule.getMaxSlots() + 1);
        classScheduleRepository.save(schedule);

        List<Booking> waitlist = bookingRepository.findFirstWaitListed(schedule.getId());
        if (!waitlist.isEmpty()) {
            Booking nextUser = waitlist.get(0);
            User waitlistUser = nextUser.getUser();

            UserPackage userPackage = userPackageRepository.findValidPackage(
                    waitlistUser.getId(), schedule.getCountry().getCode(), LocalDate.now()
            );

            if (userPackage != null && userPackage.getRemainingCredits() >= schedule.getRequiredCredits()) {
                nextUser.setStatus(BookingStatus.BOOKED);
                nextUser.setBookingTime(LocalDateTime.now());
                bookingRepository.save(nextUser);

                userPackage.setRemainingCredits(userPackage.getRemainingCredits() - schedule.getRequiredCredits());
                userPackageRepository.save(userPackage);

                schedule.setMaxSlots(schedule.getMaxSlots() - 1);
                classScheduleRepository.save(schedule);
            }
        }

        return refund ? "Booking canceled. Credit refunded." : "Booking canceled. No refund due to late cancellation.";
    }

    @Override
    @Transactional
    public String checkInToClass(String email, Long bookingId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getUser().getId().equals(user.getId())) {
            return "Unauthorized to check in.";
        }

        if (booking.getStatus() != BookingStatus.BOOKED) {
            return "Only booked classes can be checked in.";
        }

        LocalDateTime now = LocalDateTime.now();
        ClassSchedule schedule = booking.getClassSchedule();

        if (now.isBefore(schedule.getStartTime())) {
            return "You can only check in when class starts.";
        }

        if (now.isAfter(schedule.getEndTime())) {
            return "Class has already ended. You missed check-in.";
        }

        boolean paymentSuccess = paymentCharge(user, booking.getUserPackage().getaPackage());

        if (!paymentSuccess) {
            return "Payment failed. Please check your payment details.";
        }

        booking.setStatus(BookingStatus.CHECKED_IN);
        bookingRepository.save(booking);

        return "Check-in successful!";
    }

    private boolean paymentCharge(User user, Package selectedPackage) {

        UserPackage userPackage = userPackageRepository.findValidPackage(user.getId(),
                selectedPackage.getCountry().getCode(), LocalDate.now());

        if (userPackage != null && userPackage.getRemainingCredits() > 0) {
            userPackage.setRemainingCredits(userPackage.getRemainingCredits() - 1);
            userPackageRepository.save(userPackage);
            return true;
        }

        return false;
    }
}
