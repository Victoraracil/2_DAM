package com.dam;

import com.dam.DAO.LibrosDAO;
import com.dam.DAO.LibrosDAOImpl;
import com.dam.model.Autores;
import com.dam.model.Libros;
import java.util.List;
import java.util.Scanner;

public class App {
    static Scanner teclado = new Scanner(System.in);
    static LibrosDAO librosDAO = new LibrosDAOImpl();
    public static void main(String[] args) {
        boolean terminado = false;
        do {
            System.out.println("\nEscoja una opción:");
            System.out.println("1. Ver todos los Libros");
            System.out.println("2. Ver todos los autores");
            System.out.println("0. Salir");
            String opcion = teclado.nextLine();
            if (opcion.equals("1")) {
                mostrarLibros();
            } else if (opcion.equals("2")) {
                mostrarAutores();
            }
            else if (opcion.equals("0")) {
                terminado = true;
            }
        } while (!terminado);
        System.out.println("Fin del programa.");
                teclado.close();
    }
    public static void mostrarLibros() {
        List<Libros> resultados = librosDAO.obtenerLibros();
        if (resultados != null && !resultados.isEmpty()) {
            for (Libros libro : resultados) {
                System.out.println(libro.getId() + ": " + libro.getTitulo() + ", de " + (libro.getAutores()!=null ? libro.getAutores().getNombre() : "Anónimo"));
            }
        } else {
            System.out.println("No hay libros registrados o hubo un error.");
        }
    }
    public static void mostrarAutores() {
        List<Autores> resultados = librosDAO.obtenerAutores();
        if (resultados != null && !resultados.isEmpty()) {
            for (Autores autor : resultados) {
                System.out.println(autor.getCod() + ": " + autor.getNombre());
            }
        } else {
            System.out.println("No hay libros registrados o hubo un error.");
        }
    }
}

