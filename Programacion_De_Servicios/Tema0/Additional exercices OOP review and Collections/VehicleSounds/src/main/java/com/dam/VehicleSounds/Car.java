package com.dam.VehicleSounds;

public class Car extends Vehicle {

    public Car(String model) {
        super(model);
    }

    @Override
    public String sound() {
        return "Vroom vroom";
    }
}

