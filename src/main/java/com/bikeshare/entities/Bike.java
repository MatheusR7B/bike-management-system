package com.bikeshare.entities;

import com.bikeshare.enums.BikeStatus;
import com.bikeshare.enums.BikeType;

import java.util.Objects;

public class Bike extends Station {

    private int id;
    private BikeType model;
    private BikeStatus status;
    private Double mileage;

    private Station station;

    public Bike() {

    }
    public Bike(int id, BikeType model, BikeStatus status, Double mileage) {
        this.id = id;
        this.model = model;
        this.status = status;
        this.mileage = mileage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BikeType getModel() {
        return model;
    }

    public void setModel(BikeType model) {
        this.model = model;
    }

    public BikeStatus getStatus() {
        return status;
    }

    public void setStatus(BikeStatus status) {
        this.status = status;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public Station getStation() {
        return station;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Bike bike = (Bike) o;
        return id == bike.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "└── Bike #" + id + " - " + status;
    }
}
