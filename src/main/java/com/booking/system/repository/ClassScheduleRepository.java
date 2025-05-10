package com.booking.system.repository;

import com.booking.system.entity.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {

    @Query("""
        SELECT cs FROM ClassSchedule cs
        WHERE cs.country.code = :countryCode
        AND cs.startTime > CURRENT_TIMESTAMP
        AND cs.maxSlots > 0
    """)
    List<ClassSchedule> findAvailableSchedulesByCountry(@Param("countryCode") String countryCode);

    @Query("SELECT cs FROM ClassSchedule cs WHERE cs.endTime BETWEEN :from AND :to")
    List<ClassSchedule> findEndedClasses(LocalDateTime from, LocalDateTime to);
}
