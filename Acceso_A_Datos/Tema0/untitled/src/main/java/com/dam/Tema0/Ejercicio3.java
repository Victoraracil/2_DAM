package com.dam.Tema0;

import java.io.*;
import java.util.Scanner;

public class Ejercicio3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String nombreFichero = "triangle.txt";

        // --- Pedir tamaño al usuario ---
        System.out.print("Introduce el tamaño del triángulo (altura y base): ");
        int n = sc.nextInt();

        // --- Escribir los triángulos en el fichero ---
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreFichero))) {

            // Triángulo creciente
            bw.write("Triángulo creciente:");
            bw.newLine();
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= i; j++) {
                    bw.write("*");
                }
                bw.newLine();
            }

            bw.newLine(); // salto entre figuras

            // Triángulo decreciente
            bw.write("Triángulo decreciente:");
            bw.newLine();
            for (int i = n; i >= 1; i--) {
                for (int j = 1; j <= i; j++) {
                    bw.write("*");
                }
                bw.newLine();
            }

            System.out.println("Triángulos escritos correctamente en " + nombreFichero);
        } catch (IOException e) {
            System.out.println("Error al escribir en el fichero: " + e.getMessage());
        }
    }
}
