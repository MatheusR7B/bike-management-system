package com.bikeshare.entities;

import com.bikeshare.entities.dao.BikeDao;
import com.bikeshare.entities.dao.RideDao;
import com.bikeshare.enums.BikeStatus;

import java.time.Duration;
import java.time.LocalDateTime;

public class RideService {

    RideDao rideDao = new RideDao();
    BikeDao bikeDao = new BikeDao();

    public void startRide(Bike bike, Station startStation, Customer client) {
        startStation.removeBike(bike);
        bike.setStatus(BikeStatus.EM_USO);
        bikeDao.update(bike);

        Ride ride = new Ride(bike, client, startStation, LocalDateTime.now());
        rideDao.salvar(ride);
    };

    public Double finishRide(Ride ride, Station endStation) {
        endStation.addBike(ride.getBike());
        ride.getBike().setStatus(BikeStatus.DISPONIVEL);
        rideDao.update(ride);
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
        rideDao.update(ride);

        return princing;

    }

}
