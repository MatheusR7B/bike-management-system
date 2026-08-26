package com.bikeshare.entities;

import com.bikeshare.enums.BikeStatus;

import java.time.LocalDateTime;

public class RideService {

    public Ride startRide(Bike bike, Station startStation, Customer client) {
        startStation.removeBike(bike);
        bike.setStatus(BikeStatus.EM_USO);

        Ride ride = new Ride(bike, client, startStation, LocalDateTime.now());
        return ride;
    };

}
