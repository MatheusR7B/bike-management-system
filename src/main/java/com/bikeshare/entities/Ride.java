package com.bikeshare.entities;

import com.bikeshare.enums.BikeStatus;
import com.bikeshare.enums.RideStatus;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Ride {

    @Transient
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Ride() {
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Bike bike;

    @Enumerated(EnumType.STRING)
    private RideStatus status;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "start_station_id")
    private Station startStation;

    @ManyToOne
    @JoinColumn(name = "end_station_id")
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

    public Ride(Bike bike, Customer customer, Station startStation, LocalDateTime startTime) {
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
        return "Ride #" + id +
                " - Bike: #" + bike.getId() + ", " + bike.getStatus() +
                ", Cliente: " + customer.toString() +
                ", " + startStation +
                ", Inicio corrida - " + startTime.format(fmt);
    }
}
