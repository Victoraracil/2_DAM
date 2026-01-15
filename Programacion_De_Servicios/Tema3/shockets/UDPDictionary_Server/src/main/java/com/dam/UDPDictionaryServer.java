package com.dam;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;

public class UDPDictionaryServer {

    public static void main(String[] args) {

        // Diccionario Inglés -> Español
        Map<String, String> dictionary = new HashMap<>();
        dictionary.put("hello", "hola");
        dictionary.put("bye", "adiós");
        dictionary.put("house", "casa");
        dictionary.put("car", "coche");
        dictionary.put("computer", "ordenador");

        System.out.println("UDP Dictionary Server started...");

        try (DatagramSocket socket = new DatagramSocket(6000)) {

            byte[] buffer = new byte[1024];

            while (true) {
                // Recibir paquete
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                socket.receive(request);

                String word = new String(
                        request.getData(),
                        0,
                        request.getLength()
                ).toLowerCase();

                System.out.println("Received word: " + word);

                // Buscar traducción
                String translation = dictionary.get(word);

                // Si existe, se responde
                if (translation != null) {
                    byte[] responseData = translation.getBytes();

                    DatagramPacket response = new DatagramPacket(
                            responseData,
                            responseData.length,
                            request.getAddress(),
                            request.getPort()
                    );

                    socket.send(response);
                    System.out.println("Sent translation: " + translation);
                }
                // Si no existe, NO se envía nada (según enunciado)
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
