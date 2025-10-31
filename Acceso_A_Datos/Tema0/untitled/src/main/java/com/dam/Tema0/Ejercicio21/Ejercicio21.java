package com.dam.Tema0.Ejercicio21;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ejercicio21 {
    private static final String FICHERO = "amigos.txt";
    private List<Amigo> amigos;
    private Scanner scanner;

    public Ejercicio21() {
        scanner = new Scanner(System.in);
        amigos = cargarAmigos();
    }

    public static void main(String[] args) {
        Ejercicio21 agenda = new Ejercicio21();
        agenda.menu();
    }

    private void menu() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Añadir una persona");
            System.out.println("2. Ver nombres de todos");
            System.out.println("3. Buscar");
            System.out.println("4. Exportar a XML");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> añadirPersona();
                case 2 -> mostrarNombres();
                case 3 -> buscar();
                case 4 -> exportarXML();
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
                System.out.println("\nNombre: " + a.getNombre());
                System.out.println("Edad: " + a.getEdad());
                System.out.println("Email: " + a.getEmail());
                System.out.println("Comentarios: " + a.getComentarios());
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron coincidencias.");
        }
    }

    private void guardarAmigos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FICHERO))) {
            for (Amigo a : amigos) {
                pw.println(a);
            }
        } catch (IOException e) {
            System.out.println("Error al guardar amigos: " + e.getMessage());
        }
    }

    private List<Amigo> cargarAmigos() {
        List<Amigo> lista = new ArrayList<>();
        File fichero = new File(FICHERO);
        if (!fichero.exists()) return lista;

        try (BufferedReader br = new BufferedReader(new FileReader(FICHERO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";", 4);
                if (partes.length == 4) {
                    String nombre = partes[0];
                    int edad = Integer.parseInt(partes[1]);
                    String email = partes[2];
                    String comentarios = partes[3];
                    lista.add(new Amigo(nombre, edad, email, comentarios));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar amigos: " + e.getMessage());
        }
        return lista;
    }

    private void exportarXML() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("friends.xml"))) {
            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            pw.println("<amigos>");
            for (Amigo a : amigos) {
                pw.print(a.toXML());
            }
            pw.println("</amigos>");
            System.out.println("Datos exportados correctamente a friends.xml");
        } catch (IOException e) {
            System.out.println("Error al exportar a XML: " + e.getMessage());
        }
    }
}

