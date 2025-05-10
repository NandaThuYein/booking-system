package com.booking.system.dto;

import java.io.Serializable;

public class UserDTO implements Serializable {

    private Long id;

    private String email;

    private String password;

    private String name;

    private CountryDTO country;

    private boolean isVerified;

    public Long getId() {
        return id;
    }

    public UserDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserDTO setName(String name) {
        this.name = name;
        return this;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public UserDTO setCountry(CountryDTO country) {
        this.country = country;
        return this;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public UserDTO setVerified(boolean verified) {
        isVerified = verified;
        return this;
    }
}
