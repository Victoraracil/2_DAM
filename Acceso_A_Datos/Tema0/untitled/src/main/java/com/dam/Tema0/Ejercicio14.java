package com.dam.Tema0;

import java.io.*;
import java.util.Scanner;

public class Ejercicio14 {
    public static void main(String[] args) {
        String nombreArchivo = null;

        // Intentar obtener el nombre del archivo desde los argumentos
        if (args.length > 0) {
            nombreArchivo = args[0];
        } else {
            //Si no hay argumentos, pedirlo al usuario
            Scanner sc = new Scanner(System.in);
            System.out.print("Introduce el nombre del fichero a comprobar: ");
            nombreArchivo = sc.nextLine();
            sc.close();
        }

        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            System.out.println("El archivo no existe.");
            return;
        }

        try (FileInputStream fis = new FileInputStream(archivo)) {
            //Leer los dos primeros bytes
            int byte1 = fis.read();
            int byte2 = fis.read();

            if (byte1 == -1 || byte2 == -1) {
                System.out.println("El archivo está vacío o es demasiado pequeño.");
                return;
            }

            //Comprobar si corresponden a 'B' y 'M'
            if (byte1 == 'B' && byte2 == 'M') {
                System.out.println("El archivo parece un BMP válido.");
            } else {
                System.out.println("El archivo NO parece un BMP válido.");
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}

