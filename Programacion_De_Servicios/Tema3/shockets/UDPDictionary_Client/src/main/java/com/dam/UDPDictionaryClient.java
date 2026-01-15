package com.dam;

import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPDictionaryClient {

    public static void main(String[] args) {

        try (DatagramSocket socket = new DatagramSocket()) {

            // Timeout de 5 segundos
            socket.setSoTimeout(5000);

            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 6000;

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter a word in English: ");
            String word = scanner.nextLine();

            byte[] data = word.getBytes();

            // Enviar palabra
            DatagramPacket request = new DatagramPacket(
                    data,
                    data.length,
                    serverAddress,
                    serverPort
            );
            socket.send(request);

            // Preparar recepción
            byte[] buffer = new byte[1024];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);

            try {
                socket.receive(response);

                String translation = new String(
                        response.getData(),
                        0,
                        response.getLength()
                );

                System.out.println("Spanish translation: " + translation);

            } catch (InterruptedIOException e) {
                // Timeout
                System.out.println("No translation found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
