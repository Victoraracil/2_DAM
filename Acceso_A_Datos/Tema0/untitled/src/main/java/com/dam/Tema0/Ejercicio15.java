package com.dam.Tema0;

import java.io.*;
import java.util.Scanner;

public class Ejercicio15 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce el nombre del fichero BMP: ");
        String nombreArchivo = sc.nextLine();
        sc.close();

        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            System.out.println("El archivo no existe.");
            return;
        }

        try (FileInputStream fis = new FileInputStream(archivo)) {
            //Leer los primeros 54 bytes (cabecera estándar BMP)
            byte[] cabecera = new byte[54];
            int bytesLeidos = fis.read(cabecera);

            if (bytesLeidos < 54) {
                System.out.println("El archivo es demasiado pequeño para ser un BMP válido.");
                return;
            }

            //Comprobar que comienza con 'B' y 'M'
            if (cabecera[0] != 'B' || cabecera[1] != 'M') {
                System.out.println("El archivo no parece un BMP válido (no empieza con 'BM').");
                return;
            }

            //Obtener el campo de compresión (offset 30 a 33)
            // El campo está en formato little-endian (4 bytes)
            int compresion = (cabecera[30] & 0xFF)
                    | ((cabecera[31] & 0xFF) << 8)
                    | ((cabecera[32] & 0xFF) << 16)
                    | ((cabecera[33] & 0xFF) << 24);

            //Interpretar el valor del campo de compresión
            String tipoCompresion;
            switch (compresion) {
                case 0:
                    tipoCompresion = "BI_RGB (sin compresión)";
                    break;
                case 1:
                    tipoCompresion = "BI_RLE8 (compresión RLE de 8 bits)";
                    break;
                case 2:
                    tipoCompresion = "BI_RLE4 (compresión RLE de 4 bits)";
                    break;
                case 3:
                    tipoCompresion = "BI_BITFIELDS (máscara de bits)";
                    break;
                case 4:
                    tipoCompresion = "BI_JPEG (JPEG interno)";
                    break;
                case 5:
                    tipoCompresion = "BI_PNG (PNG interno)";
                    break;
                default:
                    tipoCompresion = "Desconocida (código " + compresion + ")";
                    break;
            }

            //Mostrar resultado
            System.out.println("\nResultado de análisis del BMP:");
            System.out.println(" - Cabecera: válida ('BM')");
            System.out.println(" - Tipo de compresión: " + tipoCompresion);

            if (compresion == 0)
                System.out.println("El archivo NO está comprimido.");
            else
                System.out.println("El archivo ESTÁ comprimido.");

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}

