package com.bikeshare.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Station {

    private int id;
    private String name;
    private String address;
    private int capacity;
    
    private Set<Bike> bikes = new HashSet<>();
    
    public Station() {
    }
    public Station(int id, String name, String address, int capacity) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public void addBike(Bike bike) {
        bikes.add(bike);
    }

    public void removeBike(Bike bike) {
        bikes.remove(bike);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return id == station.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Estação ").append(name).append("\n");
        for (Bike bike : this.bikes) {
            sb.append(bike.toString()).append("\n");
        }
        return sb.toString();
    }
}
