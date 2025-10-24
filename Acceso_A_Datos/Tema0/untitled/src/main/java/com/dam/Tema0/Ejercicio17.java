package com.dam.Tema0;

import java.io.*;
import java.util.*;


public class Ejercicio17 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Persona2> personas = new ArrayList<>();

        System.out.println("=== Registro de personas ===");
        boolean continuar = true;

        while (continuar) {
            System.out.print("Introduce el nombre: ");
            String nombre = sc.nextLine();

            System.out.print("Introduce el email: ");
            String email = sc.nextLine();

            System.out.print("Introduce la fecha de nacimiento (dd/mm/aaaa): ");
            String fecha = sc.nextLine();

            personas.add(new Persona2(nombre, email, fecha));

            System.out.print("¿Deseas añadir otra persona? (s/n): ");
            continuar = sc.nextLine().trim().equalsIgnoreCase("s");
        }

        System.out.print("Introduce el nombre del archivo donde guardar los datos: ");
        String nombreArchivo = sc.nextLine();
        sc.close();

        // Serializar la lista de personas
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            oos.writeObject(personas);
            System.out.println("\nDatos serializados correctamente en el archivo: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
        }

        // Leer los datos de vuelta (opcional, para comprobar)
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            List<Persona2> leidas = (List<Persona2>) ois.readObject();
            System.out.println("\nDatos leídos del archivo:");
            for (Persona2 p : leidas) {
                System.out.println(" - " + p);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al leer los datos: " + e.getMessage());
        }
    }
}

class Persona implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String email;
    private String fechaNacimiento;

    public Persona(String nombre, String email, String fechaNacimiento) {
        this.nombre = nombre;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Email: " + email + ", Fecha de nacimiento: " + fechaNacimiento;
    }
}

