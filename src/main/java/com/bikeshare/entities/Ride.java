package com.bikeshare.entities;

import java.time.LocalDateTime;

public class Ride {

    private Bike bike;
    private Customer customer;
    private Station startStation;
    private Station endStation;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Ride(Bike bike, Customer customer, Station startStation, LocalDateTime startTime) {
        this.bike = bike;
        this.customer = customer;
        this.startStation = startStation;
        this.startTime = startTime;
    }
}
