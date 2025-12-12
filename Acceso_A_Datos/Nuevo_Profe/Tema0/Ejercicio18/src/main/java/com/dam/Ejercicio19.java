package com.dam;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class Ejercicio19 {
    public static void main(String[] args) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("productos.bin"))) {

            List<Producto> productos = (List<Producto>) ois.readObject();

            productos.forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
