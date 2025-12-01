package com.dam.analysis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashSHA256 {

    /**
     * Calcula el hash SHA-256 de un archivo.
     *
     * @param filePath Ruta del archivo a procesar.
     * @return Cadena hexadecimal con el hash SHA-256.
     * @throws IOException Si el archivo no se puede leer.
     */
    public static String sha256(Path filePath) throws IOException {
        try {
            // Crear instancia del algoritmo SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Leer todos los bytes del archivo
            byte[] fileBytes = Files.readAllBytes(filePath);

            // Aplicar SHA-256
            byte[] hashBytes = digest.digest(fileBytes);

            // Convertir a hexadecimal
            return bytesToHex(hashBytes);

        } catch (NoSuchAlgorithmException e) {
            // No debería ocurrir nunca, ya que SHA-256 siempre existe en Java
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    // Convierte un array de bytes en una cadena hex
    private static String bytesToHex(byte[] hashBytes) {
        StringBuilder hex = new StringBuilder();
        for (byte b : hashBytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }
}

