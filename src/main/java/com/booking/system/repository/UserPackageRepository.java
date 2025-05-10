package com.booking.system.repository;

import com.booking.system.entity.UserPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserPackageRepository extends JpaRepository<UserPackage, Long> {

    List<UserPackage> findByUserId(Long userId);

    @Query("""
        SELECT up FROM UserPackage up
        WHERE up.user.id = :userId
        AND up.aPackage.country.code = :countryCode
        AND up.expiryDate >= :currentDate
        AND up.remainingCredits >= 1
        ORDER BY up.expiryDate ASC
    """)
    UserPackage findValidPackage(
        @Param("userId") Long userId,
        @Param("countryCode") String countryCode,
        @Param("currentDate") LocalDate currentDate
    );

    @Query("""
        SELECT up FROM UserPackage up
        WHERE up.user.id = :userId
        AND up.aPackage.country.code = :countryCode
        ORDER BY up.expiryDate DESC
    """)
    UserPackage findLatestByUserAndCountry(@Param("userId") Long userId, @Param("countryCode") String countryCode);
}
