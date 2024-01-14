package com.n1akai.e_commercetutorial.models;

import com.google.firebase.database.FirebaseDatabase;

public class Car {
    private String id, make_id, model, fuel, topSpeed;
    private int year;
    private double price;
    private String image;

    public Car() {
    }

    public Car(String id, String make_id, String model, String fuel, String topSpeed, int year, double price, String image) {
        this.id = id;
        this.make_id = make_id;
        this.model = model;
        this.fuel = fuel;
        this.topSpeed = topSpeed;
        this.year = year;
        this.price = price;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getMake_id() {
        return make_id;
    }

    public String getModel() {
        return model;
    }

    public String getFuel() {
        return fuel;
    }

    public String getTopSpeed() {
        return topSpeed;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
