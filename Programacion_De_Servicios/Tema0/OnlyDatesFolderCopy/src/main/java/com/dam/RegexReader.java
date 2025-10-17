package com.dam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexReader {
    // Regex that matches common date formats (DD/MM/YYYY, DD-MM-YYYY, YYYY-MM-DD, etc.)
    private static final Pattern DATE_PATTERN = Pattern.compile("\\b(\\d{1,2}[-/.]\\d{1,2}[-/.]\\d{2,4}|\\d{4}[-/.]\\d{1,2}[-/.]\\d{1,2})\\b");

    //Checks if the given file contains a date.
    public static boolean hasDate(File file) {
        if (!file.isFile() || !file.canRead()) {
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher matcher = DATE_PATTERN.matcher(line);
                if (matcher.find()) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + file.getName() + " -> " + e.getMessage());
        }

        return false;
    }
}

