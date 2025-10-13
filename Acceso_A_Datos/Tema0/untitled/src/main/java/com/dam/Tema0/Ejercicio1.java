package com.dam.Tema0;

import java.io.File;

public class Ejercicio1 {
    public static void main(String[] args) {
        String ruta = args.length > 0 ? args[0] : ".";
        File f = new File(ruta);

        if (f.exists()) {
            if (f.isFile()) {
                System.out.println("El tamaño es de " + f.length());
                System.out.println("Puede ejecutarse: " + f.canExecute());
                System.out.println("Puede leerse: " + f.canRead());
                System.out.println("Puede escribirse: " + f.canWrite());
            } else {
                String[] losArchivos = f.list();
                System.out.println("El directorio " + ruta + " contiene:");
                for (String archivo : losArchivos) {
                    System.out.println("\t" + archivo);
                }
            }
        } else {
            System.out.println("El fichero o ruta no existe");
        }
    }
}