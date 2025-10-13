package com.dam.Tema0;

import java.io.*;
import java.util.*;

public class Ejercicio7 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // --- Pedir nombre del fichero de entrada ---
        System.out.print("Introduce el nombre del fichero de texto a invertir: ");
        String ficheroEntrada = sc.nextLine();

        // --- Nombre del fichero de salida ---
        String ficheroSalida = "invertido_" + ficheroEntrada;

        List<String> lineas = new ArrayList<>();

        // --- Leer el fichero de entrada ---
        try (BufferedReader br = new BufferedReader(new FileReader(ficheroEntrada))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }

            // Invertir el orden de las líneas
            Collections.reverse(lineas);

            // --- Escribir en el nuevo fichero ---
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida))) {
                for (String l : lineas) {
                    bw.write(l);
                    bw.newLine();
                }
            }

            System.out.println("Contenido invertido guardado en '" + ficheroSalida + "'.");

        } catch (FileNotFoundException e) {
            System.out.println("El fichero '" + ficheroEntrada + "' no existe.");
        } catch (IOException e) {
            System.out.println("Error al procesar el fichero: " + e.getMessage());
        }
    }
}

