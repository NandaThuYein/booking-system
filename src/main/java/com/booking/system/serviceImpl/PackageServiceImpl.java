package com.booking.system.serviceImpl;

import com.booking.system.entity.Package;
import com.booking.system.repository.CountryRepository;
import com.booking.system.repository.PackageRepository;
import com.booking.system.service.PackageService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PackageServiceImpl implements PackageService {

    private final PackageRepository packageRepository;

    private final CountryRepository countryRepository;

    public PackageServiceImpl(PackageRepository packageRepository, CountryRepository countryRepository) {
        this.packageRepository = packageRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Package> getAvailablePackages(String countryCode) {
        return countryRepository.findByCode(countryCode)
            .map(packageRepository::findAvailablePackagesByCountry)
            .orElse(Collections.emptyList());
    }
}
