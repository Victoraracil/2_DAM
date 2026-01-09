package com.dam;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de recorrer directorios de forma recursiva
 * y localizar archivos MP3 con cabecera ID3v1.
 */
public class Mp3Scanner {

    /**
     * Escanea un directorio y devuelve todas las canciones MP3 encontradas.
     *
     * @param directory directorio inicial a escanear
     * @return lista de canciones encontradas
     */
    public static List<Song> scanDirectory(File directory) {
        List<Song> songs = new ArrayList<>();

        // Comprobación básica
        if (directory == null || !directory.exists() || !directory.isDirectory()) {
            return songs;
        }

        // Listamos el contenido del directorio
        File[] files = directory.listFiles();
        if (files == null) {
            return songs;
        }

        // Recorremos cada elemento
        for (File file : files) {

            // Si es un directorio, llamamos recursivamente
            if (file.isDirectory()) {
                songs.addAll(scanDirectory(file));
            }

            // Si es un archivo MP3
            else if (isMp3File(file)) {
                Song song = ID3v1Reader.read(file);
                if (song != null) {
                    songs.add(song);
                }
            }
        }

        return songs;
    }

    /**
     * Comprueba si un archivo es un MP3 por su extensión.
     *
     * @param file archivo a comprobar
     * @return true si es un archivo .mp3
     */
    private static boolean isMp3File(File file) {
        return file.isFile() &&
                file.getName().toLowerCase().endsWith(".mp3");
    }
}
