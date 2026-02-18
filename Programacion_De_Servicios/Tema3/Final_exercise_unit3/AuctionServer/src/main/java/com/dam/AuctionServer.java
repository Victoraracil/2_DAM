package com.dam;

import com.dam.model.AuctionItem;
import com.dam.model.Bid;
import com.dam.model.GameStatus;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AuctionServer {

    private static final int PORT = 6000;
    private static final int MIN_PLAYERS = 2;

    private static List<ClientHandler> clients = new CopyOnWriteArrayList<>();

    //Estado de la subasta
    private static AuctionItem currentItem;
    private static double currentPrice = 0;
    private static String currentWinner = "";


    private static long endTime;      // momento en que acaba la ronda
    private static final int ROUND_TIME = 30;      // duración inicial
    private static final int ANTISNIPE_THRESHOLD = 5;   // últimos 5 segundos
    private static final int ANTISNIPE_EXTENSION = 10; // ampliación 10 segundos


    public static void main(String[] args) throws Exception {

        //SSL CONFIGURATION
        System.setProperty("javax.net.ssl.keyStore", "server.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");

        SSLServerSocketFactory factory =
                (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket serverSocket =
                (SSLServerSocket) factory.createServerSocket(PORT);

        System.out.println("Servidor SSL iniciado en puerto " + PORT);

        // Lista de items (simula catálogo de subasta)
        List<AuctionItem> items = new ArrayList<>();
        items.add(new AuctionItem(1, "Rolex Submariner",
                "Reloj de lujo resistente al agua", 1000.0));
        items.add(new AuctionItem(2, "MacBook Pro",
                "Portátil Apple de alto rendimiento", 1500.0));

        //ACEPTAR CLIENTES EN PARALELO
        new Thread(() -> {
            try {
                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("Nuevo cliente conectado");

                    ClientHandler handler = new ClientHandler(socket);
                    clients.add(handler);
                    new Thread(handler).start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        //ESPERAR MÍNIMO DE JUGADORES
        System.out.println("Esperando al menos " + MIN_PLAYERS + " jugadores...");
        while (clients.size() < MIN_PLAYERS) {
            Thread.sleep(1000);
        }

        System.out.println("Mínimo alcanzado. Comienza la subasta.");

        //BUCLE PRINCIPAL DE SUBASTA
        for (AuctionItem item : items) {

            currentItem = item;
            currentPrice = item.getStartingPrice();

            //Enviar nuevo item a todos
            GameStatus start = new GameStatus(
                    "NUEVA SUBASTA ABIERTA",
                    currentItem,
                    currentPrice,
                    ""
            );

            System.out.println("Iniciando subasta de: " + item.getName());
            broadcast(start);

            //Esperar 30 segundos escuchando pujas
            endTime = System.currentTimeMillis() + (ROUND_TIME * 1000);

            while (System.currentTimeMillis() < endTime) {
                Thread.sleep(500); //evita consumo excesivo de CPU
            }

            //Tiempo terminado y anunciar ganador
            GameStatus end = new GameStatus(
                    "SUBASTA TERMINADA",
                    currentItem,
                    currentPrice,
                    currentWinner
            );

            System.out.println(">>> Subasta finalizada. Precio final: " + currentPrice);
            broadcast(end);

            Thread.sleep(3000); //pequeña pausa antes del siguiente item
        }

        System.out.println("Todas las subastas han terminado.");
    }


    public static synchronized void procesarPuja(Bid bid) {

        if (bid.getAmount() > currentPrice) {

            //ANTI-SNIPING

            long secondsLeft =
                    (endTime - System.currentTimeMillis()) / 1000;

            if (secondsLeft <= ANTISNIPE_THRESHOLD) {
                endTime = System.currentTimeMillis()
                        + (ANTISNIPE_EXTENSION * 1000);
                System.out.println("Anti-sniping activado");
            }

            currentPrice = bid.getAmount();
            currentWinner = bid.getBidderName();


            GameStatus update = new GameStatus(
                    "Nueva puja de " + bid.getBidderName(),
                    currentItem,
                    currentPrice,
                    bid.getBidderName()
            );

            System.out.println("Puja válida: " + bid.getBidderName() +  currentPrice);

            broadcast(update);
        } else {
            System.out.println("Puja rechazada (demasiado baja): "
                    + bid.getAmount());
        }
    }

    public static void broadcast(GameStatus status) {
        for (ClientHandler c : clients) {
            c.send(status);
        }
    }
}
