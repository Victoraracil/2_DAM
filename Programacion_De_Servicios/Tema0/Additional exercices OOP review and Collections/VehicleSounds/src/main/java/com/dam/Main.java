package com.dam;


import com.dam.VehicleSounds.Bike;
import com.dam.VehicleSounds.Car;
import com.dam.VehicleSounds.Truck;
import com.dam.VehicleSounds.VehicleSounds;

public class Main {
    public static void main(String[] args) {

        // Different types of vehicles
        Car car = new Car("Tesla Model S");
        Bike bike = new Bike("Yamaha R1");
        Truck truck = new Truck("Volvo FH16");

        //Car vs Bike
        VehicleSounds<Car, Bike> race1 = new VehicleSounds<>(car, bike);
        System.out.println("=== Car vs Bike ===");
        race1.playSounds();

        //Bike vs Truck
        VehicleSounds<Bike, Truck> race2 = new VehicleSounds<>(bike, truck);
        System.out.println("=== Bike vs Truck ===");
        race2.playSounds();

        //Truck vs Car
        VehicleSounds<Truck, Car> race3 = new VehicleSounds<>(truck, car);
        System.out.println("=== Truck vs Car ===");
        race3.playSounds();

        //Bike vs Bike (same type works too)
        VehicleSounds<Bike, Bike> race4 = new VehicleSounds<>(
                new Bike("Ducati Monster"),
                new Bike("Harley-Davidson")
        );
        System.out.println("=== Bike vs Bike ===");
        race4.playSounds();
    }
}
