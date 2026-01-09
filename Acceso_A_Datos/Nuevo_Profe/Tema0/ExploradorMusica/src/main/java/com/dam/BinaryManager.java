package com.dam;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de guardar y leer objetos Song
 * utilizando serialización en ficheros binarios.
 */
public class BinaryManager {

    /**
     * Guarda una lista de canciones en un fichero binario.
     *
     * @param songs lista de canciones a guardar
     * @param binaryFile fichero binario destino
     */
    public static void save(List<Song> songs, File binaryFile) {

        if (songs == null || binaryFile == null) {
            return;
        }

        // Si el fichero ya existe, se elimina (según el enunciado)
        if (binaryFile.exists()) {
            binaryFile.delete();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(binaryFile))) {

            oos.writeObject(songs);

        } catch (IOException e) {
            System.err.println("Error al guardar el fichero binario: " + binaryFile.getName());
        }
    }

    /**
     * Lee una lista de canciones desde un fichero binario.
     *
     * @param binaryFile fichero binario de origen
     * @return lista de canciones leídas
     */
    @SuppressWarnings("unchecked")
    public static List<Song> load(File binaryFile) {

        if (binaryFile == null || !binaryFile.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(binaryFile))) {

            return (List<Song>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al leer el fichero binario: " + binaryFile.getName());
            return new ArrayList<>();
        }
    }
}

