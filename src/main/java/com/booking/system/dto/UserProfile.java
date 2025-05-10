package com.booking.system.dto;

import java.io.Serializable;

public class UserProfile implements Serializable {

    private String email;

    private String name;

    private String country;

    public UserProfile(String email, String name, String country) {
        this.email = email;
        this.name = name;
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public UserProfile setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserProfile setName(String name) {
        this.name = name;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public UserProfile setCountry(String country) {
        this.country = country;
        return this;
    }
}
