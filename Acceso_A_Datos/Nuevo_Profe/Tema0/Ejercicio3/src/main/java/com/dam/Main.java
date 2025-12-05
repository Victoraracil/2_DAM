package com.dam;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        try (BufferedReader br = new BufferedReader(new FileReader("archivo.txt"))) {
            String linea;
            int lineas = 0;
            while ((linea = br.readLine()) != null) {
                lineas += 1;
                System.out.println("el fichero tiene " + lineas);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}