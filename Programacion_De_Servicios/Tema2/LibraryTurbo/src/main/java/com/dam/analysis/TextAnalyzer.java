package com.dam.analysis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class TextAnalyzer {

    /**
     * Cuenta el total de palabras en un archivo de texto.
     *
     * @param filePath Ruta del archivo.
     * @return Número total de palabras.
     * @throws IOException Si ocurre un error leyendo el archivo.
     */
    public static long countTotalWords(Path filePath) throws IOException {
        // Stream independiente Nº1
        try (Stream<String> lines = Files.lines(filePath)) {
            return lines
                    .flatMap(line -> Stream.of(line.split("[^A-Za-zÀ-ÿ0-9]+")))
                    .filter(token -> !token.isEmpty())
                    .count();
        }
    }

    /**
     * Cuenta cuántas veces aparece la palabra objetivo en el archivo.
     * Búsqueda estricta (coincidencia exacta, case-sensitive o insensitive según prefiera).
     *
     * @param filePath Ruta del archivo.
     * @param target   Palabra a buscar.
     * @return Número de apariciones exactas.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public static long countTargetWord(Path filePath, String target) throws IOException {
        // Stream independiente Nº2
        String lowerTarget = target.toLowerCase();

        try (Stream<String> lines = Files.lines(filePath)) {
            return lines
                    .flatMap(line -> Stream.of(line.split("[^A-Za-zÀ-ÿ0-9]+")))
                    .map(String::toLowerCase)
                    .filter(token -> token.equals(lowerTarget))
                    .count();
        }
    }
}
