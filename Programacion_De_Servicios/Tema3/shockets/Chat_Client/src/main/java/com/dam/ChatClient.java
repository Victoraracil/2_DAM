package com.dam;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) {

        try (
                Socket socket = new Socket("localhost", 6000);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );

                PrintStream out = new PrintStream(
                        socket.getOutputStream()
                );

                Scanner scanner = new Scanner(System.in)
        ) {

            System.out.println("Connected to Chat Server");

            String messageToSend;
            String receivedMessage;

            while (true) {

                // Escribir mensaje al servidor
                System.out.print("Client: ");
                messageToSend = scanner.nextLine();
                out.println(messageToSend);

                if (messageToSend.equalsIgnoreCase("bye")) {
                    break;
                }

                // Leer mensaje del servidor
                receivedMessage = in.readLine();
                System.out.println("Server: " + receivedMessage);

                if (receivedMessage.equalsIgnoreCase("bye")) {
                    break;
                }
            }

            System.out.println("Chat ended");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
