package com.dam.Tema0;

import java.io.*;
import java.util.Scanner;

public class Ejercicio13 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // Pedir al usuario el archivo y el tamaño
            System.out.print("Introduce el nombre del archivo a dividir: ");
            String nombreArchivo = sc.nextLine();

            File archivoOriginal = new File(nombreArchivo);
            if (!archivoOriginal.exists()) {
                System.out.println("El archivo no existe.");
                return;
            }

            System.out.print("Introduce el tamaño máximo de cada fragmento (en bytes): ");
            int tamFragmento = sc.nextInt();

            // Abrir flujo de entrada
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(archivoOriginal))) {
                byte[] buffer = new byte[tamFragmento];
                int bytesLeidos;
                int numParte = 1;

                while ((bytesLeidos = bis.read(buffer)) > 0) {
                    String nombreParte = nombreArchivo + ".parte" + numParte;
                    try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(nombreParte))) {
                        bos.write(buffer, 0, bytesLeidos);
                        System.out.println("Creado: " + nombreParte + " (" + bytesLeidos + " bytes)");
                    }
                    numParte++;
                }

                System.out.println("\nArchivo dividido correctamente en " + (numParte - 1) + " partes.");
            }

        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}

