package com.dam.analysis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class TextAnalyzer {

    /**
     * Reads a text file and counts how many words it contains.
     *
     * @param filePath Path to the file.
     * @return Total number of words found.
     * @throws IOException If the file cannot be opened or read.
     */

    public static long countTotalWords(Path filePath) throws IOException {
        // Separate stream #1
        try (Stream<String> lines = Files.lines(filePath)) {
            return lines
                    .flatMap(line -> Stream.of(line.split("[^A-Za-zÀ-ÿ0-9]+")))
                    .filter(token -> !token.isEmpty())
                    .count();
        }
    }

    /**
     * Counts how many times a specific word appears in the file.
     * The search is exact (case-sensitive or case-insensitive depending on how it's handled).
     *
     * @param filePath Path to the file.
     * @param target   Word to search for.
     * @return Number of exact matches found.
     * @throws IOException If there's an issue reading the file.
     */

    public static long countTargetWord(Path filePath, String target) throws IOException {
        // Separate stream #2
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
