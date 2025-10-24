package com.dam.Tema0;

import java.io.*;
import java.util.Scanner;

public class Ejercicio19 {
    public static void main(String[] args) {
        String nombreArchivo;

        // Obtener el nombre del fichero por argumentos o pedirlo al usuario
        if (args.length > 0) {
            nombreArchivo = args[0];
        } else {
            Scanner sc = new Scanner(System.in);
            System.out.print("Introduce el nombre del fichero BMP: ");
            nombreArchivo = sc.nextLine();
            sc.close();
        }

        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            System.out.println("El archivo \"" + nombreArchivo + "\" no existe.");
            return;
        }

        // Leer los primeros 54 bytes (cabecera BMP)
        try (FileInputStream fis = new FileInputStream(archivo)) {
            byte[] cabecera = new byte[54];
            int bytesLeidos = fis.read(cabecera);

            if (bytesLeidos < 54) {
                System.out.println("El archivo es demasiado pequeño para ser un BMP válido.");
                return;
            }

            // Comprobamos si los dos primeros bytes son 'B' y 'M'
            if (cabecera[0] != 'B' || cabecera[1] != 'M') {
                System.out.println("El archivo no parece un BMP válido (no empieza con 'BM').");
                return;
            }

            // Extraer ancho y alto (little-endian, 4 bytes cada uno)
            int ancho = (cabecera[18] & 0xFF) | ((cabecera[19] & 0xFF) << 8) |
                    ((cabecera[20] & 0xFF) << 16) | ((cabecera[21] & 0xFF) << 24);
            int alto  = (cabecera[22] & 0xFF) | ((cabecera[23] & 0xFF) << 8) |
                    ((cabecera[24] & 0xFF) << 16) | ((cabecera[25] & 0xFF) << 24);

            System.out.println("El archivo parece un BMP válido.");
            System.out.println("Ancho: " + ancho + " píxeles");
            System.out.println("Alto : " + alto  + " píxeles");

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}

