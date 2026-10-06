package com.bikeshare.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private LocalDate dateBirth;

    public Customer() {
    }

    public Customer(String name, String email, LocalDate dateBirth) {
        this.name = name;
        this.email = email;
        this.dateBirth = dateBirth;
    }

    @Override
    public String toString() {
        return name;
    }
}
