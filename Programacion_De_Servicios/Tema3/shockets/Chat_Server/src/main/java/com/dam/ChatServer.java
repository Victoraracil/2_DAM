package com.dam;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatServer {

    public static void main(String[] args) {

        System.out.println("Chat Server started...");

        try (
                ServerSocket serverSocket = new ServerSocket(6000);
                Socket clientSocket = serverSocket.accept();

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream())
                );

                PrintStream out = new PrintStream(
                        clientSocket.getOutputStream()
                );

                Scanner scanner = new Scanner(System.in)
        ) {

            System.out.println("Client connected");

            String receivedMessage;
            String messageToSend;

            while (true) {

                //Leer mensaje del cliente
                receivedMessage = in.readLine();
                System.out.println("Client: " + receivedMessage);

                if (receivedMessage.equalsIgnoreCase("bye")) {
                    break;
                }

                //Escribir mensaje al cliente
                System.out.print("Server: ");
                messageToSend = scanner.nextLine();
                out.println(messageToSend);

                if (messageToSend.equalsIgnoreCase("bye")) {
                    break;
                }
            }

            System.out.println("Chat ended");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

