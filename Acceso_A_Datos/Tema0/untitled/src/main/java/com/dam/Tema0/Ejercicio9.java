package com.dam.Tema0;

import java.io.*;
import java.util.*;

public class Ejercicio9 {
    public static void main(String[] args) {
        // --- Comprobar argumentos ---
        if (args.length != 3) {
            System.out.println("Uso: java OrdenarFicheros <fichero1> <fichero2> <ficheroSalida>");
            return;
        }

        String fichero1 = args[0];
        String fichero2 = args[1];
        String ficheroSalida = args[2];

        List<String> lineas = new ArrayList<>();

        // --- Leer los dos ficheros de entrada ---
        leerFichero(fichero1, lineas);
        leerFichero(fichero2, lineas);

        // --- Ordenar las líneas alfabéticamente ---
        Collections.sort(lineas);

        // --- Escribir las líneas en el fichero de salida ---
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroSalida))) {
            for (String linea : lineas) {
                bw.write(linea);
                bw.newLine();
            }
            System.out.println("Fichero '" + ficheroSalida + "' creado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el fichero de salida: " + e.getMessage());
        }
    }

    // --- Método auxiliar para leer líneas de un fichero ---
    private static void leerFichero(String nombreFichero, List<String> lista) {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lista.add(linea);
            }
        } catch (FileNotFoundException e) {
            System.out.println("El fichero '" + nombreFichero + "' no existe.");
        } catch (IOException e) {
            System.out.println("Error al leer el fichero '" + nombreFichero + "': " + e.getMessage());
        }
    }
}
