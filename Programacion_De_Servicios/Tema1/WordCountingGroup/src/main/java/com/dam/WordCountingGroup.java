package com.dam;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WordCountingGroup {
    //Runnable class that processes a file
    static class FileProcessor implements Runnable {
        private File file;

        public FileProcessor(File file) {
            this.file = file;
        }

        @Override
        public void run() {
            int wordCount = 0;
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNext()) {
                    scanner.next();
                    wordCount++;
                }
                System.out.println("File: " + file.getName() + " | Word count: " + wordCount);
            } catch (FileNotFoundException e) {
                System.err.println("Could not read file: " + file.getName());
            }
        }
    }
}
