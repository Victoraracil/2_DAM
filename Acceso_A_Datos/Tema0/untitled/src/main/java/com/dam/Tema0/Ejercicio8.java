package com.dam.Tema0;

import java.io.*;
import java.util.Scanner;

public class Ejercicio8 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // --- Pedir el nombre del fichero ---
        System.out.print("Introduce el nombre del fichero de texto: ");
        String nombreFichero = sc.nextLine();

        int contadorVocales = 0;

        // --- Leer el fichero y contar vocales ---
        try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                // Convertir a minúsculas para simplificar
                linea = linea.toLowerCase();

                // Recorrer cada carácter de la línea
                for (char c : linea.toCharArray()) {
                    if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                        contadorVocales++;
                    }
                }
            }

            // Mostrar el resultado
            System.out.println("El fichero '" + nombreFichero + "' contiene " + contadorVocales + " vocales.");

        } catch (FileNotFoundException e) {
            System.out.println("El fichero '" + nombreFichero + "' no existe.");
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
    }
}

