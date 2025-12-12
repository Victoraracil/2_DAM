package com.dam;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Ejercicio18 {
    public static void main(String[] args) {
        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto(1, "Ratón gamer", 19.99));
        productos.add(new Producto(2, "Teclado mecánico", 49.99));
        productos.add(new Producto(3, "Monitor 144Hz", 199.99));

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("productos.bin"))) {

            oos.writeObject(productos);
            System.out.println("Lista guardada correctamente en productos.bin");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
