package com.dam.model;

import java.io.Serializable;

public class Bid implements Serializable {

    private int itemId;
    private String bidderName;
    private double amount;
    private boolean isWinningBid;

    public Bid(int itemId, String bidderName, double amount) {
        this.itemId = itemId;
        this.bidderName = bidderName;
        this.amount = amount;
        this.isWinningBid = false;
    }

    public int getItemId() {
        return itemId;
    }

    public String getBidderName() {
        return bidderName;
    }

    public double getAmount() {
        return amount;
    }

    public void setWinningBid(boolean winningBid) {
        this.isWinningBid = winningBid;
    }
}
