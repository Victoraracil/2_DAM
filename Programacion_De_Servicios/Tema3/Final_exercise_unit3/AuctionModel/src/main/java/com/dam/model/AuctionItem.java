package com.dam.model;

import java.io.Serializable;

public class AuctionItem implements Serializable {

    private int id;
    private String name;
    private String description;
    private double startingPrice;

    public AuctionItem(int id, String name, String description, double startingPrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startingPrice = startingPrice;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getStartingPrice() {
        return startingPrice;
    }
}
