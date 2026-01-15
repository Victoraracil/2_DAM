package com.dam;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPChatServer {

    public static void main(String[] args) {

        System.out.println("UDP Chat Server started on port 6000");

        try (
                DatagramSocket socket = new DatagramSocket(6000);
                Scanner scanner = new Scanner(System.in)
        ) {
            byte[] buffer = new byte[1024];

            while (true) {
                // Receive message
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                socket.receive(request);

                String message = new String(
                        request.getData(),
                        0,
                        request.getLength()
                );

                InetAddress clientAddress = request.getAddress();
                int clientPort = request.getPort();

                System.out.println(
                        "Message from " + clientAddress.getHostAddress() +
                                ":" + clientPort + " -> " + message
                );

                // If client says bye, still respond and continue listening
                System.out.print("Server reply: ");
                String responseText = scanner.nextLine();

                byte[] responseData = responseText.getBytes();

                DatagramPacket response = new DatagramPacket(
                        responseData,
                        responseData.length,
                        clientAddress,
                        clientPort
                );

                socket.send(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
