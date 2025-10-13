package com.dam.Tema0;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Ejercicio5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String nombreFichero = "log.txt";

        // --- Pedir texto al usuario ---
        System.out.print("Introduce un texto para el log: ");
        String textoUsuario = sc.nextLine();

        // --- Obtener fecha y hora actual ---
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");

        String fecha = ahora.format(formatoFecha);
        String hora = ahora.format(formatoHora);

        // --- Crear línea de log ---
        String lineaLog = fecha + " " + hora + " -> " + textoUsuario;

        // --- Añadir línea al fichero ---
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreFichero, true))) {
            bw.write(lineaLog);
            bw.newLine(); // salto de línea
            System.out.println("Línea añadida correctamente a " + nombreFichero);
        } catch (IOException e) {
            System.out.println("Error al escribir en el fichero: " + e.getMessage());
        }
    }
}

