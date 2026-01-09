package com.dam;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

/**
 * Clase encargada de leer la cabecera ID3v1 de un archivo MP3.
 * La cabecera ID3v1 ocupa los últimos 128 bytes del fichero.
 */
public class ID3v1Reader {

    /**
     * Lee la cabecera ID3v1 de un archivo MP3.
     *
     * @param mp3File archivo MP3 del que se quiere leer la cabecera
     * @return un objeto Song con los datos ID3v1 o null si no existe cabecera
     */
    public static Song read(File mp3File) {

        // Comprobaciones básicas de seguridad
        if (mp3File == null || !mp3File.exists() || mp3File.length() < 128) {
            return null;
        }

        // RandomAccessFile permite acceder directamente al final del archivo
        try (RandomAccessFile raf = new RandomAccessFile(mp3File, "r")) {

            // Nos movemos a los últimos 128 bytes del archivo
            raf.seek(raf.length() - 128);

            // Leemos la cabecera completa en un bloque
            byte[] buffer = new byte[128];
            raf.readFully(buffer);

            // Los primeros 3 bytes deben ser "TAG"
            String tag = new String(buffer, 0, 3, StandardCharsets.ISO_8859_1);
            if (!"TAG".equals(tag)) {
                return null; // No tiene cabecera ID3v1
            }

            // Extraemos los campos según el estándar ID3v1
            String title = new String(buffer, 3, 30, StandardCharsets.ISO_8859_1).trim();
            String artist = new String(buffer, 33, 30, StandardCharsets.ISO_8859_1).trim();
            String album = new String(buffer, 63, 30, StandardCharsets.ISO_8859_1).trim();
            String year = new String(buffer, 93, 4, StandardCharsets.ISO_8859_1).trim();
            String comment = new String(buffer, 97, 30, StandardCharsets.ISO_8859_1).trim();

            // El género es un byte sin signo
            int genre = buffer[127] & 0xFF;

            // Creamos y devolvemos el objeto Song
            return new Song(
                    mp3File.getAbsolutePath(),
                    title,
                    artist,
                    album,
                    year,
                    comment,
                    genre
            );

        } catch (IOException e) {
            System.err.println("Error leyendo la cabecera ID3v1 de: " + mp3File.getName());
            return null;
        }
    }
}
