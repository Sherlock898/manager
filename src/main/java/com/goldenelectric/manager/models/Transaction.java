package com.goldenelectric.manager.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "transactions")
public class Transaction {
    // Define the Status 
    // CHARGING: The transaction is in progress
    // COMPLETED: The transaction has been completed
    // CANCELLED: The transaction has been cancelled

    public enum Status {
        CHARGING, COMPLETED, CANCELLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Date startDate;
    private Date endDate;
    private Status status;

    // Define the meter values, reading of the energy consumption
    @NotNull
    private Long startMeterValue;
    private Long endMeterValue;

    @Column(updatable = false)
    private Date createdAt;
    private Date updatedAt;

    // User Relationship, one user can have multiple transactions
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    // Lifecycle Methods
    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

    // Getters and Setters
    public Transaction() {}
    public Transaction(Date startDate, Date endDate, Status status, Long startMeterValue, Long endMeterValue, User user) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.startMeterValue = startMeterValue;
        this.endMeterValue = endMeterValue;
        this.user = user;
    }
    public Long getId() {return id;}
    public Date getStartDate() {return startDate;}
    public void setStartDate(Date startDate) {this.startDate = startDate;}
    public Date getEndDate() {return endDate;}
    public void setEndDate(Date endDate) {this.endDate = endDate;}
    public Status getStatus() {return status;}
    public void setStatus(Status status) {this.status = status;}
    public Long getStartMeterValue() {return startMeterValue;}
    public void setStartMeterValue(Long startMeterValue) {this.startMeterValue = startMeterValue;}
    public Long getEndMeterValue() {return endMeterValue;}
    public void setEndMeterValue(Long endMeterValue) {this.endMeterValue = endMeterValue;}
    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}
    public Date getCreatedAt() {return createdAt;}
    public Date getUpdatedAt() {return updatedAt;}   

}
