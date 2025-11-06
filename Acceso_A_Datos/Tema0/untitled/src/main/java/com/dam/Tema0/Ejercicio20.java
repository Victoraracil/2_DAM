package com.dam.Tema0;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Ejercicio20 {
    private static final String FICHERO = "amigos.dat";
    private List<Amigo> amigos;
    private Scanner scanner;

    public Ejercicio20() {
        scanner = new Scanner(System.in);
        amigos = cargarAmigos();
    }

    public static void main(String[] args) {
        Ejercicio20 agenda = new Ejercicio20();
        agenda.menu();
    }

    private void menu() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Añadir una persona");
            System.out.println("2. Ver nombres de todos");
            System.out.println("3. Buscar");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> añadirPersona();
                case 2 -> mostrarNombres();
                case 3 -> buscar();
                case 0 -> guardarAmigos();
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void añadirPersona() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Edad: ");
        int edad = Integer.parseInt(scanner.nextLine());
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Comentarios: ");
        String comentarios = scanner.nextLine();

        Amigo nuevoAmigo = new Amigo(nombre, edad, email, comentarios);
        amigos.add(nuevoAmigo);
        guardarAmigos();
        System.out.println("Amigo añadido correctamente.");
    }

    private void mostrarNombres() {
        if (amigos.isEmpty()) {
            System.out.println("No hay amigos guardados.");
        } else {
            System.out.println("--- Nombres de amigos ---");
            for (Amigo a : amigos) {
                System.out.println(a.getNombre());
            }
        }
    }

    private void buscar() {
        System.out.print("Introduce término de búsqueda: ");
        String termino = scanner.nextLine().toLowerCase();

        boolean encontrado = false;
        for (Amigo a : amigos) {
            if (a.getNombre().toLowerCase().contains(termino) ||
                    a.getEmail().toLowerCase().contains(termino) ||
                    a.getComentarios().toLowerCase().contains(termino)) {
                System.out.println("\n" + a);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron coincidencias.");
        }
    }

    private void guardarAmigos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHERO))) {
            oos.writeObject(amigos);
        } catch (IOException e) {
            System.out.println("Error al guardar amigos: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private List<Amigo> cargarAmigos() {
        File fichero = new File(FICHERO);
        if (!fichero.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FICHERO))) {
            return (List<Amigo>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar amigos: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}

// Clase que representa a un amigo y es serializable
class Amigo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private int edad;
    private String email;
    private String comentarios;

    public Amigo(String nombre, int edad, String email, String comentarios) {
        this.nombre = nombre;
        this.edad = edad;
        this.email = email;
        this.comentarios = comentarios;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getComentarios() {
        return comentarios;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + "\nEdad: " + edad + "\nEmail: " + email + "\nComentarios: " + comentarios;
    }
}

