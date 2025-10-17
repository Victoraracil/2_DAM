package com.dam.VehicleSounds;

// Generic class that can hold two Vehicle-derived objects(different types)
public class VehicleSounds<T extends Vehicle, U extends Vehicle> {

    private T vehicle1;
    private U vehicle2;

    public VehicleSounds(T vehicle1, U vehicle2) {
        this.vehicle1 = vehicle1;
        this.vehicle2 = vehicle2;
    }

    public T getVehicle1() {
        return vehicle1;
    }

    public void setVehicle1(T vehicle1) {
        this.vehicle1 = vehicle1;
    }

    public U getVehicle2() {
        return vehicle2;
    }

    public void setVehicle2(U vehicle2) {
        this.vehicle2 = vehicle2;
    }

    public void playSounds() {
        System.out.println(vehicle1.getModel() + " says: " + vehicle1.sound());
        System.out.println(vehicle2.getModel() + " says: " + vehicle2.sound());
        System.out.println();
    }
}
