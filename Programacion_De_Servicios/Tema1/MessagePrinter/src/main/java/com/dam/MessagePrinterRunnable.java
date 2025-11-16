package com.dam;

public class MessagePrinterRunnable implements Runnable {
    private String message;

    //Constructor
    public MessagePrinterRunnable(String message) {
        this.message = message;
    }

    //Prints the message
    @Override
    public void run() {
        System.out.println(message);
    }
}
