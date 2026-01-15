package com.dam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

    public static void main(String[] args) {

        try (
                Socket socket = new Socket("localhost", 6000);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
                PrintWriter out = new PrintWriter(
                        socket.getOutputStream(), true
                );
                Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Connected to Echo Server");

            String message;
            String response;

            while (true) {
                System.out.print("Enter message: ");
                message = scanner.nextLine();

                out.println(message);// Envía al servidor
                response = in.readLine();// Espera respuesta

                System.out.println("Echo: " + response);

                if (message.equalsIgnoreCase("bye")) {
                    break;
                }
            }

            System.out.println("Client disconnected");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
