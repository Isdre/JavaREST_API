package com.example.springapi.api.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String name;
    private LocalDate dob;
    @Transient
    private Integer age;

    public User(int id, String name, LocalDate dob) {
        this.id = Long.valueOf(id);
        this.name = name;
        this.dob = dob;
    }

    public User(String name, LocalDate dob) {
        this.name = name;
        this.dob = dob;
    }


    public User() {}

    public Long getId() {return id;}

    public void setId(int id) {this.id = Long.valueOf(id);}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public LocalDate getDob() {return dob;}

    public void setDob(LocalDate dob) {this.dob = dob;}

    public Integer getAge() {return Period.between(this.dob, LocalDate.now()).getYears();}

}
