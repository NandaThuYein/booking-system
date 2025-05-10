package com.booking.system.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "package")
public class Package implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "packageSequenceGenerator")
    @SequenceGenerator(
        name = "packageSequenceGenerator",
        sequenceName = "package_sequence_generator",
        allocationSize = 1000
    )
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    private Country country;

    @Column(name = "credits", nullable = false)
    private int credits;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    public Long getId() {
        return id;
    }

    public Package setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Package setName(String name) {
        this.name = name;
        return this;
    }

    public Country getCountry() {
        return country;
    }

    public Package setCountry(Country country) {
        this.country = country;
        return this;
    }

    public int getCredits() {
        return credits;
    }

    public Package setCredits(int credits) {
        this.credits = credits;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Package setPrice(double price) {
        this.price = price;
        return this;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public Package setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }
}
