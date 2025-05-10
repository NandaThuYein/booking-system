package com.booking.system.repository;

import com.booking.system.entity.Country;
import com.booking.system.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {

    @Query("SELECT p FROM Package p WHERE p.country = :country AND p.expiryDate > CURRENT_DATE")
    List<Package> findAvailablePackagesByCountry(@Param("country") Country country);
}
