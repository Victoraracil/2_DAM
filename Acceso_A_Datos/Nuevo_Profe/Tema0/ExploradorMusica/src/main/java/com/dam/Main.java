package com.dam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal de la aplicación.
 * Gestiona los modos de ejecución:
 * -E (Escanear)
 * -L (Leer)
 */
public class Main {

    public static void main(String[] args) {

        // Comprobación básica de argumentos
        if (args.length < 2) {
            showUsage();
            return;
        }

        String mode = args[0];

        switch (mode) {

            case "-E":
                executeScanMode(args);
                break;

            case "-L":
                executeReadMode(args);
                break;

            default:
                showUsage();
        }
    }

    /**
     * Modo -E: escanea directorios y genera el fichero binario.
     */
    private static void executeScanMode(String[] args) {

        if (args.length != 3) {
            showUsage();
            return;
        }

        File textFile = new File(args[1]);
        File binaryFile = new File(args[2]);

        if (!textFile.exists()) {
            System.err.println("El archivo de rutas no existe.");
            return;
        }

        List<Song> allSongs = new ArrayList<>();

        // Leemos el archivo de texto línea a línea
        try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {

            String line;
            while ((line = br.readLine()) != null) {

                File directory = new File(line.trim());

                if (directory.exists() && directory.isDirectory()) {
                    allSongs.addAll(Mp3Scanner.scanDirectory(directory));
                }
            }

        } catch (IOException e) {
            System.err.println("Error leyendo el archivo de rutas.");
            return;
        }

        // Guardamos las canciones encontradas
        BinaryManager.save(allSongs, binaryFile);

        System.out.println("Exploración finalizada.");
        System.out.println("Canciones encontradas: " + allSongs.size());
    }

    /**
     * Modo -L: lee y muestra el fichero binario.
     */
    private static void executeReadMode(String[] args) {

        if (args.length != 2) {
            showUsage();
            return;
        }

        File binaryFile = new File(args[1]);

        List<Song> songs = BinaryManager.load(binaryFile);

        if (songs.isEmpty()) {
            System.out.println("No hay canciones para mostrar.");
            return;
        }

        for (Song song : songs) {
            System.out.println(song);
        }
    }

    /**
     * Muestra la forma correcta de ejecutar el programa.
     */
    private static void showUsage() {
        System.out.println("Uso del programa:");
        System.out.println("Modo Escanear:");
        System.out.println("  -E <archivo_rutas.txt> <archivo_binario>");
        System.out.println("Modo Leer:");
        System.out.println("  -L <archivo_binario>");
    }
}
