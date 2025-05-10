package com.booking.system.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class UserPackageResponse implements Serializable {

    private Long id;

    private String packageName;

    private String countryCode;

    private int remainingCredits;

    private LocalDate expiryDate;

    private boolean expired;

    public Long getId() {
        return id;
    }

    public UserPackageResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getPackageName() {
        return packageName;
    }

    public UserPackageResponse setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public UserPackageResponse setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public int getRemainingCredits() {
        return remainingCredits;
    }

    public UserPackageResponse setRemainingCredits(int remainingCredits) {
        this.remainingCredits = remainingCredits;
        return this;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public UserPackageResponse setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public boolean isExpired() {
        return expired;
    }

    public UserPackageResponse setExpired(boolean expired) {
        this.expired = expired;
        return this;
    }
}
