package com.bikeshare.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Customer {

    private int id;
    private String name;
    private String email;
    private LocalDate dateBirth;

    public Customer(int id, String name, String email, LocalDate dateBirth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateBirth = dateBirth;
    }

    @Override
    public String toString() {
        return name;
    }
}
