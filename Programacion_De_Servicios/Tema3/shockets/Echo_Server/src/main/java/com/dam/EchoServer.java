package com.dam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    public static void main(String[] args) {

        System.out.println("Echo Server starting...");

        try (ServerSocket serverSocket = new ServerSocket(6000)) {
            System.out.println("Server listening on port 6000");

            // Espera a que un cliente se conecte
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())
            );

            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true
            );

            String message;

            // Lee mensajes del cliente
            while ((message = in.readLine()) != null) {
                System.out.println("Received: " + message);

                if (message.equalsIgnoreCase("bye")) {
                    out.println("BYE");
                    break;
                }

                // Devuelve el mensaje en mayúsculas
                out.println(message.toUpperCase());
            }

            clientSocket.close();
            System.out.println("Connection closed");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
