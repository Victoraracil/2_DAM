package com.dam.VehicleSounds;

public class Bike extends Vehicle {

    public Bike(String model) {
        super(model);
    }

    @Override
    public String sound() {
        return "Brmm brmm";
    }
}
