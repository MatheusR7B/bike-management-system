package com.bikeshare.entities;

import com.bikeshare.enums.BikeStatus;
import com.bikeshare.enums.RideStatus;

import java.time.LocalDateTime;

public class Ride {

    private Bike bike;
    private RideStatus status;
    private Customer customer;
    private Station startStation;
    private Station endStation;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

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

    public Ride(Bike bike, Customer customer, Station startStation, LocalDateTime startTime) {
        this.bike = bike;
        this.customer = customer;
        this.startStation = startStation;
        this.startTime = startTime;
        status = RideStatus.EM_ANDAMENTO;
    }

    public void finish(Station endStation, LocalDateTime endTime) {
        this.endStation = endStation;
        this.endTime = endTime;
        status = RideStatus.FINALIZADA;
    }

}
