package com.dam;


import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        // Obtener la fecha y hora actual
        LocalDateTime ahora = LocalDateTime.now();
        // Formatear la fecha y hora
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaHora = ahora.format(formato);

        // La línea que vamos a escribir en el archivo
        String linea = "Ejecución del programa: " + fechaHora;

        // Intentar escribir en el archivo "log.txt"
        try (FileWriter fw = new FileWriter("log.txt", true)) { // 'true' para añadir, no sobrescribir
            fw.write(linea + System.lineSeparator()); // Añadimos un salto de línea
            System.out.println("Registro añadido correctamente.");
        } catch (IOException e) {
            System.out.println("Ocurrió un error al escribir en el archivo: " + e.getMessage());
        }
    }
}