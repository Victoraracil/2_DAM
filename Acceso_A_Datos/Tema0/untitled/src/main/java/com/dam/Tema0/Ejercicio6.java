package com.dam.Tema0;

import java.io.*;
import java.util.Scanner;

public class Ejercicio6 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String nombreFichero = "rectangulo.txt";

        // --- Pedir dimensiones ---
        System.out.print("Introduce el número de filas: ");
        int filas = sc.nextInt();

        System.out.print("Introduce el número de columnas: ");
        int columnas = sc.nextInt();

        // --- Escribir el rectángulo en el fichero ---
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreFichero))) {

            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    bw.write("*");
                }
                bw.newLine(); // Salto de línea al final de cada fila
            }

            System.out.println("Rectángulo de " + filas + "x" + columnas +
                    " creado en '" + nombreFichero + "'.");

        } catch (IOException e) {
            System.out.println("Error al escribir en el fichero: " + e.getMessage());
        }
    }
}

