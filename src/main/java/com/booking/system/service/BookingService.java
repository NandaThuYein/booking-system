package com.booking.system.service;

import com.booking.system.entity.User;

public interface BookingService {

    public String bookClass(String email, Long classId);

    public String cancelBooking(String email, Long bookingId);

    public String checkInToClass(String email, Long bookingId);
}
