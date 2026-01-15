package com.dam;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPChatClient {

    public static void main(String[] args) {

        try (
                DatagramSocket socket = new DatagramSocket();
                Scanner scanner = new Scanner(System.in)
        ) {

            System.out.print("Enter server IP: ");
            String serverIp = scanner.nextLine();

            InetAddress serverAddress = InetAddress.getByName(serverIp);
            int serverPort = 6000;

            while (true) {
                System.out.print("Client message: ");
                String message = scanner.nextLine();

                byte[] data = message.getBytes();

                DatagramPacket request = new DatagramPacket(
                        data,
                        data.length,
                        serverAddress,
                        serverPort
                );

                socket.send(request);

                if (message.equalsIgnoreCase("bye")) {
                    System.out.println("Client finished");
                    break;
                }

                // Receive response
                byte[] buffer = new byte[1024];
                DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                socket.receive(response);

                String serverResponse = new String(
                        response.getData(),
                        0,
                        response.getLength()
                );

                System.out.println("Server: " + serverResponse);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

