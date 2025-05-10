package com.booking.system.repository;

import com.booking.system.entity.Booking;
import com.booking.system.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("""
    SELECT b FROM Booking b
    WHERE b.user.id = :userId
    AND b.status = 'BOOKED'
        AND (
            (:startTime BETWEEN b.classSchedule.startTime AND b.classSchedule.endTime)
            OR (:endTime BETWEEN b.classSchedule.startTime AND b.classSchedule.endTime)
            OR (b.classSchedule.startTime BETWEEN :startTime AND :endTime)
        )
    """)
    List<Booking> findOverlappingBookings(
            @Param("userId") Long userId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    @Query("""
        SELECT b FROM Booking b
        WHERE b.classSchedule.id = :classId
        AND b.status = 'WAIT_LISTED'
        ORDER BY b.bookingTime ASC
    """)
    List<Booking> findFirstWaitListed(@Param("classId") Long classId);

    List<Booking> findByClassScheduleIdAndStatus(Long classId, BookingStatus status);
}
