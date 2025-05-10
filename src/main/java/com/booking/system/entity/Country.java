package com.booking.system.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "country")
public class Country extends AbstractAuditingEntity  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "countrySequenceGenerator")
    @SequenceGenerator(
            name = "countrySequenceGenerator",
            sequenceName = "country_sequence_generator",
            allocationSize = 1000
    )
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public Country setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Country setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public Country setName(String name) {
        this.name = name;
        return this;
    }
}
