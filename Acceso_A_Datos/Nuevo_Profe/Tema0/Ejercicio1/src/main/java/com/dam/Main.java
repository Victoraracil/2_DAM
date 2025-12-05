package com.dam;

import java.io.File;
public class Main {
    public static void main(String[] args) {
        String ruta = "C:/Users/Victor Aracil/Desktop";
        ListarArachivos(ruta);
    }

    public static void ListarArachivos (String ruta) {
        File directorio = new File(ruta);

        File[] archivos = directorio.listFiles();
        if (archivos == null) return;

        for (File archivo : archivos) {
            System.out.println(archivo.getAbsolutePath());

            //Si es un directorio, volver a llamar recursivamente
            if (archivo.isDirectory()) {
                ListarArachivos(archivo.getAbsolutePath());
            }

        }

    }
}