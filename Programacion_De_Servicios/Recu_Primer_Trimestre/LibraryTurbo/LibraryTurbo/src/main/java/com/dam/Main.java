package com.dam;

import com.dam.analysis.ReportVerifier;
import com.dam.output.ReportWriter;

import java.nio.file.*;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Ask and validate folder
        Path folder;
        while (true) {
            System.out.print("Enter folder to process: ");
            String input = scanner.nextLine().trim();

            folder = Paths.get(input).toAbsolutePath();

            if (Files.exists(folder) && Files.isDirectory(folder)) {
                break;
            }

            System.out.println("Invalid directory. Try again.");
        }

        //ask mode (g or v)
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

    //Generate mode
    private static void runGenerateMode(Path folder, Scanner scanner) throws Exception {

        //Ask word
        System.out.print("Enter target word: ");
        String target = scanner.nextLine().trim();

        //Create report writer
        ReportWriter writer = new ReportWriter(folder);

        //searc files .txt and .md
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

        //Create ThreadPool
        int threads = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threads);

        AtomicInteger completed = new AtomicInteger(0);
        int total = filesToProcess.size();

        //Progres programes every 1 second
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {

            int active = executor.getActiveCount();
            int queued = executor.getQueue().size();
            int done = completed.get();

            System.out.println("Progress: " + done + "/" + total +
                    " | active=" + active + " | queued=" + queued);

        }, 0, 1, TimeUnit.SECONDS);

        //Send tasks
        for (Path file : filesToProcess) {
            executor.submit(() -> {
                writer.processFile(file, target);
                completed.incrementAndGet();
            });
        }

        //Close ThreadPool
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.MINUTES);

        //Stop scheduler when all is complete
        scheduler.shutdown();
        scheduler.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("\n✔ Report generation completed.");
    }

    //Verify modo
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
