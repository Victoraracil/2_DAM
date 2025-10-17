package com.dam;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class Main {
    public static void main(String[] args) {
        //Change the path to try
        String sourceFolderPath = "C:\\Users\\Victor Aracil\\Documents\\file1.txt";
        String destinationFolderPath = "Dates";

        File sourceFolder = new File(sourceFolderPath);
        File destinationFolder = new File(destinationFolderPath);

        //Validate source folder
        if (!sourceFolder.exists() || !sourceFolder.isDirectory()) {
            System.err.println("The source folder does not exist or is not a directory.");
            return;
        }

        //Remove existing Dates folder if it exists
        if (destinationFolder.exists()) {
            deleteFolderRecursively(destinationFolder);
        }

        //Create new Dates folder
        if (destinationFolder.mkdir()) {
            System.out.println("Created folder: " + destinationFolder.getAbsolutePath());
        }

        //Scan source folder and copy only files with dates
        File[] files = sourceFolder.listFiles();
        if (files == null) {
            System.err.println("The source folder is empty or could not be read.");
            return;
        }

        int copiedCount = 0;
        for (File file : files) {
            if (file.isFile() && RegexReader.hasDate(file)) {
                Path sourcePath = file.toPath();
                Path destinationPath = Paths.get(destinationFolderPath, file.getName());

                try {
                    Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                    copiedCount++;
                    System.out.println("Copied file with date: " + file.getName());
                } catch (IOException e) {
                    System.err.println("Failed to copy " + file.getName() + ": " + e.getMessage());
                }
            }
        }

        System.out.println("\nCopy complete. Total files copied: " + copiedCount);
    }

    //Deletes a folder and all its contents recursively.
    private static void deleteFolderRecursively(File folder) {
        File[] contents = folder.listFiles();
        if (contents != null) {
            for (File f : contents) {
                if (f.isDirectory()) {
                    deleteFolderRecursively(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }
}
