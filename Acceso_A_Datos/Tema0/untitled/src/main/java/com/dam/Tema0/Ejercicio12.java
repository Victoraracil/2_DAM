package com.dam.Tema0;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio12 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Puedes usar nombres prefijados o pedirlos al usuario
        System.out.print("Introduce el nombre del fichero origen: ");
        String ficheroOrigen = sc.nextLine();

        System.out.print("Introduce el nombre del fichero destino: ");
        String ficheroDestino = sc.nextLine();

        try (
                FileInputStream fis = new FileInputStream(ficheroOrigen);
                FileOutputStream fos = new FileOutputStream(ficheroDestino)
        ) {
            // Obtenemos el tamaño del fichero origen
            int tamano = fis.available();

            // Creamos un bloque de bytes del mismo tamaño
            byte[] bloque = new byte[tamano];

            // Leemos todo el fichero de una vez
            int bytesLeidos = fis.read(bloque);

            // Escribimos todo el bloque en el fichero destino
            fos.write(bloque, 0, bytesLeidos);

            System.out.println("Copia completada correctamente.");
            System.out.println("Se copiaron " + bytesLeidos + " bytes de " + ficheroOrigen + " a " + ficheroDestino);

        } catch (IOException e) {
            System.err.println("Error al copiar el fichero: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
