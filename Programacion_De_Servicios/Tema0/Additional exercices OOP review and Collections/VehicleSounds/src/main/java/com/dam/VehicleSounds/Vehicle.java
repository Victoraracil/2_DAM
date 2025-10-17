package com.dam.VehicleSounds;

public abstract class Vehicle {
    protected String model;

    public Vehicle(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    // Abstract method that must be implemented by subclasses
    public abstract String sound();
}
