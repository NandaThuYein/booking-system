package com.booking.system.dto;

import java.io.Serializable;

public class CountryDTO implements Serializable {

    private Long id;

    private String code;

    private String name;

    public Long getId() {
        return id;
    }

    public CountryDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return code;
    }

    public CountryDTO setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public CountryDTO setName(String name) {
        this.name = name;
        return this;
    }
}
