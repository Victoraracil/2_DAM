package com.dam;

import com.dam.model.Bid;
import com.dam.model.GameStatus;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object obj = in.readObject();

                if (obj instanceof Bid bid) {
                    System.out.println(
                            "Puja recibida: " + bid.getBidderName()
                                    + " -> " + bid.getAmount());
                    
                    AuctionServer.procesarPuja(bid);
                }
            }
        } catch (Exception e) {
            System.out.println("Cliente desconectado");
        }
    }


    public void send(GameStatus status) {
        try {
            out.writeObject(status);
            out.flush();
            out.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
