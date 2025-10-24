package com.dam.Tema0;

import java.io.*;
import java.util.Scanner;

public class Ejercicio16 {

    public static void main(String[] args) {
        String nombreArchivo = null;
        if (args.length > 0) {
            nombreArchivo = args[0];
        } else {
            Scanner sc = new Scanner(System.in);
            System.out.print("Introduce el nombre del fichero MP3: ");
            nombreArchivo = sc.nextLine();
            sc.close();
        }

        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            System.out.println("El archivo \"" + nombreArchivo + "\" no existe.");
            return;
        }
        if (!archivo.isFile()) {
            System.out.println("\"" + nombreArchivo + "\" no es un archivo regular.");
            return;
        }

        // Tamaño mínimo: debe tener al menos 128 bytes para la etiqueta ID3v1
        if (archivo.length() < 128) {
            System.out.println("El archivo es demasiado pequeño para contener una etiqueta ID3v1.");
            return;
        }

        try (RandomAccessFile raf = new RandomAccessFile(archivo, "r")) {
            // Mover al final -128 bytes
            raf.seek(archivo.length() - 128);

            byte[] bloque = new byte[128];
            raf.readFully(bloque);

            // Verificar que los primeros 3 bytes del bloque son "TAG"
            String tag = new String(bloque, 0, 3, "ISO-8859-1");
            if (!"TAG".equals(tag)) {
                System.out.println("No se encontró etiqueta ID3v1 (no empieza con \"TAG\").");
                return;
            }

            String titulo    = new String(bloque, 3, 30, "ISO-8859-1").trim();
            String artista   = new String(bloque, 33, 30, "ISO-8859-1").trim();
            String album     = new String(bloque, 63, 30, "ISO-8859-1").trim();
            String año       = new String(bloque, 93, 4,  "ISO-8859-1").trim();
            String comentario= new String(bloque, 97, 30, "ISO-8859-1").trim();
            int generoByte   = bloque[127] & 0xFF;

            System.out.println("Etiqueta ID3v1 encontrada:");
            System.out.println("  Título     : " + titulo);
            System.out.println("  Artista    : " + artista);
            System.out.println("  Álbum      : " + album);
            System.out.println("  Año        : " + año);
            System.out.println("  Comentario : " + comentario);
            System.out.println("  Género     : " + generoByte + " (código)");

            // Opcional: mostrar un mapa de géneros si lo deseas
            // Por simplicidad, no lo incluimos completo aquí

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}

