package com.bikeshare.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Station {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;
    private int capacity;

    @OneToMany(mappedBy = "station")
    private Set<Bike> bikes = new HashSet<>();
    
    public Station() {
    }
    public Station(String name, String address, int capacity) {
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

    public Set<Bike> getBikes() {
        return bikes;
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
        return sb.toString();
    }
}
