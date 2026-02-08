package com.dam.model;

import java.io.Serializable;

public class GameStatus implements Serializable {

    private String message;
    private AuctionItem currentItem;
    private double currentHighestBid;
    private String winnerName;

    public GameStatus(String message, AuctionItem currentItem,
                      double currentHighestBid, String winnerName) {
        this.message = message;
        this.currentItem = currentItem;
        this.currentHighestBid = currentHighestBid;
        this.winnerName = winnerName;
    }

    public String getMessage() {
        return message;
    }

    public AuctionItem getCurrentItem() {
        return currentItem;
    }

    public double getCurrentHighestBid() {
        return currentHighestBid;
    }

    public String getWinnerName() {
        return winnerName;
    }
}
