package com.dam.Tema0;

import java.io.*;
import java.util.*;


public class Ejercicio18 {
    public static void main(String[] args) {
        String nombreArchivo = "personas.dat"; // Fichero predeterminado

        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            System.out.println("No se encuentra el archivo \"" + nombreArchivo + "\".");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            List<Persona2> personas = (List<Persona2>) ois.readObject();

            System.out.println("Datos deserializados correctamente desde \"" + nombreArchivo + "\":\n");
            for (Persona2 p : personas) {
                System.out.println(" - " + p);
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}

class Persona2 implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String email;
    private String fechaNacimiento;

    public Persona2(String nombre, String email, String fechaNacimiento) {
        this.nombre = nombre;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Email: " + email + ", Fecha de nacimiento: " + fechaNacimiento;
    }
}

