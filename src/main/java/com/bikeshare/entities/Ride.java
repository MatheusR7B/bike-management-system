package com.bikeshare.entities;

import com.bikeshare.enums.BikeStatus;
import com.bikeshare.enums.RideStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ride {

    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private int id;
    private Bike bike;
    private RideStatus status;
    private Customer customer;
    private Station startStation;
    private Station endStation;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long duration;
    private Double princing;

    public Bike getBike() {
        return bike;
    }

    public RideStatus getStatus() {
        return status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Station getStartStation() {
        return startStation;
    }

    public Station getEndStation() {
        return endStation;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Long getDuration() {
        return duration;
    }

    public Ride(int id, Bike bike, Customer customer, Station startStation, LocalDateTime startTime) {
        this.id = id;
        this.bike = bike;
        this.customer = customer;
        this.startStation = startStation;
        this.startTime = startTime;
        status = RideStatus.EM_ANDAMENTO;
    }

    public void finish(Station endStation, LocalDateTime endTime, Long duration, double princing) {
        this.endStation = endStation;
        this.endTime = endTime;
        this.duration = duration;
        this.princing = princing;
        status = RideStatus.FINALIZADA;
    }

    @Override
    public String toString() {
        return "Ride # " + id +
                " bike=" + bike +
                ", Cliente - " + customer.toString() +
                " " + startStation +
                ", Inicio corrida - " + startTime.format(fmt);
    }
}
