package com.dam.analysis;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class ReportVerifier {

    private final Path reportFolder;
    private final Path inconsistenciesFile;
    private final Path verificationLog;
    private final Object lock = new Object(); // para escribir de forma segura

    public ReportVerifier(Path reportFolder) throws IOException {
        this.reportFolder = reportFolder;

        this.inconsistenciesFile = reportFolder.resolve("inconsistencies.txt");
        this.verificationLog = reportFolder.resolve("verification.log");

        // Crear archivos si no existen
        if (!Files.exists(inconsistenciesFile)) {
            Files.createFile(inconsistenciesFile);
        }
        if (!Files.exists(verificationLog)) {
            Files.createFile(verificationLog);
        }
    }

    /**
     * Verifica todos los JSON de la carpeta de reportes.
     *
     * @return true si todos los informes son consistentes.
     * @throws InterruptedException si se interrumpe invokeAll.
     * @throws IOException          si hay errores de lectura.
     */
    public boolean verifyReports() throws InterruptedException, IOException {

        List<Path> jsonFiles = listJsonFiles();

        // Crear un pool proporcional al nº de archivos
        ExecutorService executor = Executors.newFixedThreadPool(
                Math.min(jsonFiles.size(), Runtime.getRuntime().availableProcessors())
        );

        List<Callable<Boolean>> tasks = new ArrayList<>();

        for (Path json : jsonFiles) {
            tasks.add(() -> verifySingleReport(json));
        }

        // Ejecutar todos
        List<Future<Boolean>> futures = executor.invokeAll(tasks);

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        // devolver true solo si TODOS fueron true
        return futures.stream().allMatch(f -> {
            try {
                return f.get();
            } catch (Exception e) {
                return false;
            }
        });
    }

    /**
     * Verifica un único archivo JSON.
     */
    private boolean verifySingleReport(Path jsonFile) {
        try {
            log("Verifying: " + jsonFile.getFileName());

            String content = Files.readString(jsonFile, StandardCharsets.UTF_8);

            // ------------------------------
            //  EXTRAER CAMPOS USANDO STREAMS
            // ------------------------------

            String pathValue = extractField(content, "path");
            String shaValue = extractField(content, "sha256");

            if (pathValue == null || shaValue == null) {
                registerInconsistency("Malformed JSON: " + jsonFile.getFileName());
                log("Malformed JSON: " + jsonFile.getFileName());
                return false;
            }

            // El path dentro del JSON puede no ser relativo → convertir a Path
            Path referencedFile = Paths.get(pathValue);

            if (!Files.exists(referencedFile)) {
                registerInconsistency("Missing file: " + pathValue
                        + " (from " + jsonFile.getFileName() + ")");
                log("Missing file: " + pathValue);
                return false;
            }

            // Calcular SHA real
            String realSha = HashSHA256.sha256(referencedFile);

            if (!realSha.equals(shaValue)) {
                registerInconsistency("SHA mismatch: " + referencedFile
                        + " (from " + jsonFile.getFileName() + ")");
                log("SHA mismatch");
                return false;
            }

            log("OK: " + jsonFile.getFileName());
            return true;

        } catch (Exception e) {
            log("Exception verifying " + jsonFile.getFileName() + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Extrae un campo del JSON usando Streams y split (sin librerías JSON).
     */
    private String extractField(String jsonContent, String fieldName) {
        try (Stream<String> lines = jsonContent.lines()) {
            return lines
                    .filter(line -> line.contains("\"" + fieldName + "\""))
                    .map(line -> {
                        String[] parts = line.split(":", 2);
                        if (parts.length < 2) return null;

                        String raw = parts[1].trim();

                        if (raw.endsWith(","))
                            raw = raw.substring(0, raw.length() - 1);

                        raw = raw.replace("\"", "").trim();

                        return raw;
                    })
                    .findFirst()
                    .orElse(null);
        }
    }

    /**
     * Busca todos los JSON dentro del directorio de reportes.
     */
    private List<Path> listJsonFiles() throws IOException {
        try (Stream<Path> files = Files.list(reportFolder)) {
            return files
                    .filter(f -> f.toString().endsWith(".json"))
                    .toList();
        }
    }

    private void registerInconsistency(String message) {
        synchronized (lock) {
            try {
                Files.writeString(
                        inconsistenciesFile,
                        message + System.lineSeparator(),
                        StandardOpenOption.APPEND
                );
            } catch (IOException ignored) {}
        }
    }

    private void log(String message) {
        synchronized (lock) {
            try {
                Files.writeString(
                        verificationLog,
                        message + System.lineSeparator(),
                        StandardOpenOption.APPEND
                );
            } catch (IOException ignored) {}
        }
    }
}
