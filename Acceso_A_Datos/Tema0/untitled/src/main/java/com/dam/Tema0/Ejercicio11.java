package com.dam.Tema0;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Ejercicio11 {
    public static void main(String[] args) {
        // Prefijos de nombres de fichero (puedes cambiarlos o recibirlos por args)
        String nombreFicheroBinario = "entrada.bin";
        String nombreFicheroTexto = "salida.txt";

        try (
                FileInputStream fis = new FileInputStream(nombreFicheroBinario);
                FileWriter fw = new FileWriter(nombreFicheroTexto)
        ) {
            int byteLeido;

            while ((byteLeido = fis.read()) != -1) {
                // Filtra los caracteres imprimibles (32–127)
                if (byteLeido >= 32 && byteLeido <= 127) {
                    fw.write((char) byteLeido);
                }
            }

            System.out.println("Proceso completado. Se han volcado los caracteres imprimibles a " + nombreFicheroTexto);
        } catch (IOException e) {
            System.err.println("Error al procesar los ficheros: " + e.getMessage());
        }
    }
}
