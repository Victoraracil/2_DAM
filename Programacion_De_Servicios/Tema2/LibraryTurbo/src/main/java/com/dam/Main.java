package com.dam;

import com.dam.analysis.ReportVerifier;
import com.dam.output.ReportWriter;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // -------------------------------------------------------
        //           1) PEDIR Y VALIDAR CARPETA
        // -------------------------------------------------------
        Path folder;
        while (true) {
            System.out.print("Enter folder to process: ");
            String input = scanner.nextLine().trim();

            folder = Paths.get(input);

            if (Files.exists(folder) && Files.isDirectory(folder)) {
                break;
            }

            System.out.println("Invalid directory. Try again.");
        }

        // -------------------------------------------------------
        //           2) PEDIR MODO (G o V)
        // -------------------------------------------------------
        String mode;
        while (true) {
            System.out.print("Choose mode - Generate Reports (G) / Verify Reports (V): ");
            mode = scanner.nextLine().trim().toUpperCase();

            if (mode.equals("G") || mode.equals("V")) break;

            System.out.println("Invalid option. Type G or V.");
        }

        try {
            if (mode.equals("G")) {
                runGenerateMode(folder, scanner);
            } else {
                runVerifyMode(folder);
            }
        } catch (Exception e) {
            System.out.println("Fatal error: " + e.getMessage());
        }
    }

    // =====================================================================
    //                           MODO GENERAR
    // =====================================================================
    private static void runGenerateMode(Path folder, Scanner scanner) throws Exception {

        // Pedir palabra objetivo
        System.out.print("Enter target word: ");
        String target = scanner.nextLine().trim();

        // Crear escritor de reportes
        ReportWriter writer = new ReportWriter(folder);

        // Buscar archivos .txt y .md recursivamente
        List<Path> filesToProcess;
        try (Stream<Path> stream = Files.walk(folder)) {
            filesToProcess = stream
                    .filter(p -> !Files.isDirectory(p))
                    .filter(p -> {
                        String name = p.toString().toLowerCase();
                        return name.endsWith(".txt") || name.endsWith(".md");
                    })
                    .toList();
        }

        System.out.println("Found " + filesToProcess.size() + " files.");

        // Crear ThreadPool
        int threads = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threads);

        AtomicInteger completed = new AtomicInteger(0);
        int total = filesToProcess.size();

        // Programador de progreso cada 1 segundo
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {

            int active = executor.getActiveCount();
            int queued = executor.getQueue().size();
            int done = completed.get();

            System.out.println("Progress: " + done + "/" + total +
                    " | active=" + active + " | queued=" + queued);

        }, 0, 1, TimeUnit.SECONDS);

        // Enviar tareas
        for (Path file : filesToProcess) {
            executor.submit(() -> {
                writer.processFile(file, target);
                completed.incrementAndGet();
            });
        }

        // Cerrar el ThreadPool correctamente
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.MINUTES);

        // Detener el scheduler cuando todo esté completado
        scheduler.shutdown();
        scheduler.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("\n✔ Report generation completed.");
    }

    // =====================================================================
    //                           MODO VERIFICAR
    // =====================================================================
    private static void runVerifyMode(Path originalFolder) throws Exception {

        String folderName = originalFolder.getFileName().toString();
        Path reportFolder = originalFolder.getParent().resolve("_report" + folderName);

        if (!Files.exists(reportFolder)) {
            System.out.println("No report folder found: " + reportFolder);
            return;
        }

        System.out.println("Verifying reports inside: " + reportFolder);

        ReportVerifier verifier = new ReportVerifier(reportFolder);

        boolean ok = verifier.verifyReports();

        if (ok) {
            System.out.println("\n✔ All reports are consistent.");
        } else {
            System.out.println("\n✘ Not all reports are consistent.");
            System.out.println("Check inconsistencies.txt for details.");
        }
    }
}
