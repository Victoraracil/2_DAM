package com.dam.Tema0;

import java.io.*;
import java.nio.file.*;

public class Ejercicio10 {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: java CSharpToJavaConverter <entrada.cs> <salida.java>");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];

        try {
            // Leer todas las líneas del fichero C#
            java.util.List<String> lines = Files.readAllLines(Paths.get(inputFile));

            // Escribir el fichero de salida en Java
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile))) {
                for (String line : lines) {
                    String converted = convertLine(line);
                    writer.write(converted);
                    writer.newLine();
                }
            }

            System.out.println("Conversión completada. Archivo generado: " + outputFile);
        } catch (IOException e) {
            System.out.println("Error durante la conversión: " + e.getMessage());
        }
    }

    private static String convertLine(String line) {
        String trimmed = line.trim();
        String converted = line;

        // 1. Convertir using → import
        if (trimmed.startsWith("using ")) {
            converted = line.replace("using ", "import ");
            return converted;
        }

        // 2. namespace → package
        if (trimmed.startsWith("namespace ")) {
            String name = trimmed.replace("namespace", "").trim().replace("{", "").trim();
            converted = "package " + name + ";";
            return converted;
        }

        // 3. Console.WriteLine → System.out.println
        if (line.contains("Console.WriteLine")) {
            converted = line.replace("Console.WriteLine", "System.out.println");
        }

        // 4. Conversión de tipos
        converted = converted.replace("string", "String");
        converted = converted.replace("bool", "boolean");
        // int, char y double no necesitan conversión

        // 5. Ajustar Main a Java
        if (converted.contains("static void Main")) {
            converted = converted.replace("static void Main", "public static void main");
        }

        // 6. Ajuste de llaves (Java suele abrir llaves en la misma línea)
        converted = converted.replace(") {", ") {");
        converted = converted.replace(")\n{", ") {");

        return converted;
    }
}

