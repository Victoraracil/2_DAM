package com.dam;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        //In my case C:\Users\Victor Aracil\Documents\examples\examples
        System.out.print("Enter the directory path: ");
        String dirPath = input.nextLine();
        File dir = new File(dirPath);

        if (!dir.exists() || !dir.isDirectory()) {
            System.err.println("Invalid directory.");
            input.close();
            return;
        }

        //Create ThreadGroup
        ThreadGroup group = new ThreadGroup("FileProcessorGroup");

        //Get list of files in the directory
        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
        if (files == null || files.length == 0) {
            System.out.println("No .txt files found in the directory.");
            input.close();
            return;
        }

        //Start a thread for each file
        for (File file : files) {
            Thread thread = new Thread(group, new WordCountingGroup.FileProcessor(file));
            thread.start();
        }

        //Monitor active threads
        while (group.activeCount() > 0) {
            System.out.println("Files pending to process: " + group.activeCount());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("Monitoring interrupted.");
            }
        }

        System.out.println("All files have been processed!");
        input.close();
    }
}