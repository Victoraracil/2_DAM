package com.dam.Tema0;

import java.io.*;
import java.util.Scanner;

public class Ejercicio4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String nombreFichero = "rectangle.txt";

        // --- Pedir dimensiones ---
        System.out.print("Introduce el ancho del rectángulo: ");
        int ancho = sc.nextInt();
        System.out.print("Introduce el alto del rectángulo: ");
        int alto = sc.nextInt();

        // --- Escribir rectángulo hueco en el fichero ---
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreFichero))) {

            for (int i = 1; i <= alto; i++) {
                for (int j = 1; j <= ancho; j++) {
                    // Primera o última fila
                    if (i == 1 || i == alto) {
                        bw.write("*");
                    }
                    // En filas intermedias: solo los bordes
                    else {
                        if (j == 1 || j == ancho) {
                            bw.write("*");
                        } else {
                            bw.write(" "); // hueco interior
                        }
                    }
                }
                bw.newLine(); // salto de línea al terminar cada fila
            }

            System.out.println("Rectángulo escrito correctamente en " + nombreFichero);
        } catch (IOException e) {
            System.out.println("Error al escribir en el fichero: " + e.getMessage());
        }
    }
}