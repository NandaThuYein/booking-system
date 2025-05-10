package com.booking.system.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "user_package")
public class UserPackage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userPackageSequenceGenerator")
    @SequenceGenerator(
        name = "userPackageSequenceGenerator",
        sequenceName = "user_package_sequence_generator",
        allocationSize = 1000
    )
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Package aPackage;

    @Column(name = "remaining_credits", nullable = false)
    private int remainingCredits;

    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "expired", nullable = false)
    private boolean expired;

    public Long getId() {
        return id;
    }

    public UserPackage setId(Long id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public UserPackage setUser(User user) {
        this.user = user;
        return this;
    }

    public Package getaPackage() {
        return aPackage;
    }

    public UserPackage setaPackage(Package aPackage) {
        this.aPackage = aPackage;
        return this;
    }

    public int getRemainingCredits() {
        return remainingCredits;
    }

    public UserPackage setRemainingCredits(int remainingCredits) {
        this.remainingCredits = remainingCredits;
        return this;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public UserPackage setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public UserPackage setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public boolean isExpired() {
        return expired;
    }

    public UserPackage setExpired(boolean expired) {
        this.expired = expired;
        return this;
    }
}
