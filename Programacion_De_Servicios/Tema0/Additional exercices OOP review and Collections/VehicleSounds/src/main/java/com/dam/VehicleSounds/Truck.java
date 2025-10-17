package com.dam.VehicleSounds;

public class Truck extends Vehicle {

    public Truck(String model) {
        super(model);
    }

    @Override
    public String sound() {
        return "Honk honk";
    }
}

