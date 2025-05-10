package com.booking.system.service;

import com.booking.system.entity.Package;

import java.util.List;

public interface PackageService {

    public List<Package> getAvailablePackages(String countryCode);

}
