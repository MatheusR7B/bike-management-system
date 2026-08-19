package com.bikeshare.entities;

import com.bikeshare.enums.BikeStatus;

import java.util.Objects;

public class Bike {

    private int id;
    private String model;
    private String type;
    private BikeStatus status;
    private Double mileage;

    public Bike() {

    }
    public Bike(int id, String model, String type, BikeStatus status, Double mileage) {
        this.id = id;
        this.model = model;
        this.type = type;
        this.status = status;
        this.mileage = mileage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
