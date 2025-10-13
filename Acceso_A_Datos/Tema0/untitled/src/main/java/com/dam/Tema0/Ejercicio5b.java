package com.dam.Tema0;

import java.io.*;
import java.util.Scanner;

public class Ejercicio5b {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String nombreFichero = "log.txt";

        // --- Comprobar si log.txt existe ---
        File fichero = new File(nombreFichero);

        if (!fichero.exists()) {
            System.out.println("El fichero 'log.txt' no existe.");
            System.out.print("Introduce el nombre de otro fichero: ");
            nombreFichero = sc.nextLine();
            fichero = new File(nombreFichero);
        }

        // --- Intentar leer el fichero elegido ---
        if (fichero.exists()) {
            System.out.println("Leyendo contenido de: " + nombreFichero);
            try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    System.out.println(linea);
                }
            } catch (IOException e) {
                System.out.println("Error al leer el fichero: " + e.getMessage());
            }
        } else {
            System.out.println("El fichero especificado no existe.");
        }
    }
}

