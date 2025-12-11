package com.dam.output;

import com.dam.analysis.HashSHA256;
import com.dam.analysis.TextAnalyzer;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.OffsetDateTime;
import java.util.concurrent.locks.ReentrantLock;

public class ReportWriter {

    private final Path reportFolder;     // _reportOriginal folder
    private final Path errorsLog;        // errors.log
    private final ReentrantLock lock;    // Used to write logs in a thread-safe way

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
     * Processes a file: calculates its SHA, analyzes the words, and creates the JSON report.
     *
     * @param file   Path to the file.
     * @param target Word to count in the file.
     */

    public void processFile(Path file, String target) {

        try {
            //Calculate SHA
            String sha = HashSHA256.sha256(file);

            //Take the first 16 characters to use as the JSON file name
            String prefix = sha.substring(0, 16);

            //Analize palabras
            long totalWords = TextAnalyzer.countTotalWords(file);
            long targetCount = TextAnalyzer.countTargetWord(file, target);

            //Create JSON
            String jsonContent = createJson(file, sha, totalWords, targetCount);

            //JSON File name
            String safeName = file.getFileName().toString().replace(" ", "_");
            Path outJson = reportFolder.resolve(prefix + "_" + safeName + ".json");

            //Write JSON
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
     * Creates the JSON content following the format specified in the assignment.
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
     * Writes an error message to errors.log in a thread-safe way.
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
