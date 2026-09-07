package com.bikeshare.entities;

import com.bikeshare.enums.BikeStatus;

import java.time.Duration;
import java.time.LocalDateTime;

public class RideService {

    public Ride startRide(int id, Bike bike, Station startStation, Customer client) {
        startStation.removeBike(bike);
        bike.setStatus(BikeStatus.EM_USO);

        Ride ride = new Ride(id, bike, client, startStation, LocalDateTime.now());
        return ride;
    };

    public Double finishRide(Ride ride, Station endStation) {
        endStation.addBike(ride.getBike());
        ride.getBike().setStatus(BikeStatus.DISPONIVEL);
        LocalDateTime now = LocalDateTime.now();

        Long duration = Duration.between(ride.getStartTime(), now).toMinutes();
        double princing = 0.0;
        if (duration <= 30) {
            princing = 4.0;
        }
        else {
            Long minExcendentes = duration - 30;
            princing = 4.0 + 2 * Math.ceilDiv(minExcendentes, 10);
        }

        ride.finish(endStation, now, duration, princing);

        return princing;

    }

}
