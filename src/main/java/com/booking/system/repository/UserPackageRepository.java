package com.booking.system.repository;

import com.booking.system.entity.UserPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPackageRepository extends JpaRepository<UserPackage, Long> {
}
