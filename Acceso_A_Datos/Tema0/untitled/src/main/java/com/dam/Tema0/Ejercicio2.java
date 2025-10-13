package com.dam.Tema0;

import java.io.*;
import java.nio.file.*;
import java.util.List;

public class Ejercicio2 {
    public static void main(String[] args) {
        String nombreFichero = "impares.txt";

        // --- ESCRIBIR con BufferedWriter ---
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreFichero))) {
            for (int i = 1; i <= 10; i++) {
                if (i % 2 != 0) { // número impar
                    bw.write(Integer.toString(i));
                    bw.newLine(); // salto de línea
                }
            }
            bw.write("Fin del fichero");
            bw.newLine();
            System.out.println("Escritura completada en " + nombreFichero);
        } catch (IOException e) {
            System.out.println("Error al escribir: " + e.getMessage());
        }

        // --- LEER con BufferedReader ---
        System.out.println("\nLectura con BufferedReader:");
        try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }

        // --- LEER con readAllLines ---
        System.out.println("\nLectura con Files.readAllLines:");
        try {
            List<String> lineas = Files.readAllLines(Paths.get(nombreFichero));
            for (String linea : lineas) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer con readAllLines: " + e.getMessage());
        }
    }
}