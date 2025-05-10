package com.booking.system.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "class_schedule")
public class ClassSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "classScheduleSequenceGenerator")
    @SequenceGenerator(
        name = "classScheduleSequenceGenerator",
        sequenceName = "class_schedule_sequence_generator",
        allocationSize = 1000
    )
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne
    private Country country;

    @Column(name = "required_credits", nullable = false)
    private int requiredCredits;

    @Column(name = "max_slots", nullable = false)
    private int maxSlots;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "completed")
    private boolean completed;

    @OneToMany(mappedBy = "classSchedule", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Booking> bookings;

    public Long getId() {
        return id;
    }

    public ClassSchedule setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ClassSchedule setTitle(String title) {
        this.title = title;
        return this;
    }

    public Country getCountry() {
        return country;
    }

    public ClassSchedule setCountry(Country country) {
        this.country = country;
        return this;
    }

    public int getRequiredCredits() {
        return requiredCredits;
    }

    public ClassSchedule setRequiredCredits(int requiredCredits) {
        this.requiredCredits = requiredCredits;
        return this;
    }

    public int getMaxSlots() {
        return maxSlots;
    }

    public ClassSchedule setMaxSlots(int maxSlots) {
        this.maxSlots = maxSlots;
        return this;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public ClassSchedule setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public ClassSchedule setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public boolean isCompleted() {
        return completed;
    }

    public ClassSchedule setCompleted(boolean completed) {
        this.completed = completed;
        return this;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public ClassSchedule setBookings(List<Booking> bookings) {
        this.bookings = bookings;
        return this;
    }
}

