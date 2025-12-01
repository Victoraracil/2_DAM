package com.dam.output;

import com.dam.analysis.HashSHA256;
import com.dam.analysis.TextAnalyzer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.OffsetDateTime;
import java.util.concurrent.locks.ReentrantLock;

public class ReportWriter {

    private final Path reportFolder;     // Carpeta _reportOriginal
    private final Path errorsLog;        // errors.log
    private final ReentrantLock lock;    // Para escribir logs de forma thread-safe

    public ReportWriter(Path originalFolder) throws IOException {

        String folderName = originalFolder.getFileName().toString();
        this.reportFolder = originalFolder.getParent().resolve("_report" + folderName);

        if (!Files.exists(reportFolder)) {
            Files.createDirectory(reportFolder);
        }

        this.errorsLog = reportFolder.resolve("errors.log");

        if (!Files.exists(errorsLog)) {
            Files.createFile(errorsLog);
        }

        this.lock = new ReentrantLock();
    }

    /**
     * Procesa un archivo: calcula SHA, analiza palabras y genera el JSON.
     *
     * @param file Ruta del archivo.
     * @param target Palabra objetivo a contar.
     */
    public void processFile(Path file, String target) {

        try {
            // Calcular SHA
            String sha = HashSHA256.sha256(file);

            // Tomar primeros 16 chars para el nombre del JSON
            String prefix = sha.substring(0, 16);

            // Analizar palabras
            long totalWords = TextAnalyzer.countTotalWords(file);
            long targetCount = TextAnalyzer.countTargetWord(file, target);

            // Crear JSON
            String jsonContent = createJson(file, sha, totalWords, targetCount);

            // Nombre del archivo JSON
            String safeName = file.getFileName().toString().replace(" ", "_");
            Path outJson = reportFolder.resolve(prefix + "_" + safeName + ".json");

            // Escribir JSON
            Files.writeString(
                    outJson,
                    jsonContent,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );

        } catch (Exception e) {
            writeError("Error processing file " + file + ": " + e.getMessage());
        }
    }

    /**
     * Crea el contenido JSON respetando el formato del enunciado.
     */
    private String createJson(Path file, String sha, long total, long targetCount) {

        String forwardPath = file.toString().replace('\\', '/');

        return "{\n" +
                "  \"path\": \"" + forwardPath + "\",\n" +
                "  \"processedAt\": \"" + OffsetDateTime.now() + "\",\n" +
                "  \"totalWords\": " + total + ",\n" +
                "  \"targetCount\": " + targetCount + ",\n" +
                "  \"sha256\": \"" + sha + "\"\n" +
                "}";
    }

    /**
     * Escribe un error en errors.log (thread-safe).
     */
    private void writeError(String message) {
        lock.lock();
        try {
            Files.writeString(
                    errorsLog,
                    message + System.lineSeparator(),
                    StandardOpenOption.APPEND
            );
        } catch (IOException ignored) {
        } finally {
            lock.unlock();
        }
    }
}
