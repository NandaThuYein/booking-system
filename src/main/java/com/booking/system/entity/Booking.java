package com.booking.system.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookingSequenceGenerator")
    @SequenceGenerator(
        name = "bookingSequenceGenerator",
        sequenceName = "booking_sequence_generator",
        allocationSize = 1000
    )
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private ClassSchedule classSchedule;

    @ManyToOne
    private UserPackage userPackage;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Column(name = "booking_time", nullable = false)
    private LocalDateTime bookingTime;

    @Column(name = "cancel_time")
    private LocalDateTime cancelTime;

    public Long getId() {
        return id;
    }

    public Booking setId(Long id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Booking setUser(User user) {
        this.user = user;
        return this;
    }

    public ClassSchedule getClassSchedule() {
        return classSchedule;
    }

    public Booking setClassSchedule(ClassSchedule classSchedule) {
        this.classSchedule = classSchedule;
        return this;
    }

    public UserPackage getUserPackage() {
        return userPackage;
    }

    public Booking setUserPackage(UserPackage userPackage) {
        this.userPackage = userPackage;
        return this;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public Booking setStatus(BookingStatus status) {
        this.status = status;
        return this;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public Booking setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
        return this;
    }

    public LocalDateTime getCancelTime() {
        return cancelTime;
    }

    public Booking setCancelTime(LocalDateTime cancelTime) {
        this.cancelTime = cancelTime;
        return this;
    }
}
