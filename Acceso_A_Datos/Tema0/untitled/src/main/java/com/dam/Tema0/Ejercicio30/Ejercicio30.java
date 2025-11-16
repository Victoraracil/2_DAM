package com.dam.Tema0.Ejercicio30;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

    public class Ejercicio30 {

        private static final String FILE_PATH = "coches.json";
        private static final Scanner sc = new Scanner(System.in);

        public static void main(String[] args) {
            int opcion;
            do {
                System.out.println("\n=== MENÚ DE LECTURA DE COCHES ===");
                System.out.println("1. Leer con API de Modelos");
                System.out.println("2. Leer con API de Streaming");
                System.out.println("3. Leer con API de GSON (DOM)");
                System.out.println("0. Salir");
                System.out.print("Elige una opción: ");
                opcion = leerEntero();

                switch (opcion) {
                    case 1 -> leerConModelos();
                    case 2 -> leerConStreaming();
                    case 3 -> leerConGson();
                    case 0 -> System.out.println("Saliendo del programa...");
                    default -> System.out.println("Opción no válida.");
                }
            } while (opcion != 0);
        }

        //MÉTODO 1: API DE MODELOS
        private static void leerConModelos() {
            try (FileReader reader = new FileReader(FILE_PATH)) {
                Gson gson = new Gson();
                JsonObject root = gson.fromJson(reader, JsonObject.class);
                ListaCoches lista = gson.fromJson(root.getAsJsonObject("coches"), ListaCoches.class);

                String marcaBuscada = pedirMarca();
                mostrarResultados(lista.coche, marcaBuscada);

            } catch (IOException e) {
                System.out.println("Error leyendo el fichero: " + e.getMessage());
            }
        }

        //MÉTODO 2: API DE STREAMING
        private static void leerConStreaming() {
            List<Coche> coches = new ArrayList<>();
            try (JsonReader reader = new JsonReader(new FileReader(FILE_PATH))) {
                reader.beginObject(); // "coches"
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    if (name.equals("coches")) {
                        reader.beginObject(); // "coche"
                        while (reader.hasNext()) {
                            String name2 = reader.nextName();
                            if (name2.equals("coche")) {
                                reader.beginArray();
                                while (reader.hasNext()) {
                                    Coche c = new Coche();
                                    reader.beginObject();
                                    while (reader.hasNext()) {
                                        switch (reader.nextName()) {
                                            case "marca" -> c.marca = reader.nextString();
                                            case "modelo" -> c.modelo = reader.nextString();
                                            case "cilindrada" -> c.cilindrada = reader.nextInt();
                                            default -> reader.skipValue();
                                        }
                                    }
                                    reader.endObject();
                                    coches.add(c);
                                }
                                reader.endArray();
                            } else reader.skipValue();
                        }
                        reader.endObject();
                    } else reader.skipValue();
                }
                reader.endObject();

                String marcaBuscada = pedirMarca();
                mostrarResultados(coches, marcaBuscada);

            } catch (IOException e) {
                System.out.println("Error leyendo el fichero: " + e.getMessage());
            }
        }

        //MÉTODO 3: API DE GSON (DOM)
        private static void leerConGson() {
            try (FileReader reader = new FileReader(FILE_PATH)) {
                JsonElement root = JsonParser.parseReader(reader);
                JsonArray array = root.getAsJsonObject()
                        .getAsJsonObject("coches")
                        .getAsJsonArray("coche");

                List<Coche> coches = new ArrayList<>();
                for (JsonElement elem : array) {
                    JsonObject obj = elem.getAsJsonObject();
                    Coche c = new Coche();
                    c.marca = obj.get("marca").getAsString();
                    c.modelo = obj.get("modelo").getAsString();
                    c.cilindrada = obj.get("cilindrada").getAsInt();
                    coches.add(c);
                }

                String marcaBuscada = pedirMarca();
                mostrarResultados(coches, marcaBuscada);

            } catch (IOException e) {
                System.out.println("Error leyendo el fichero: " + e.getMessage());
            }
        }

        //FUNCIONES AUXILIARES

        private static String pedirMarca() {
            System.out.print("Introduce la marca a buscar: ");
            return sc.nextLine().trim().toLowerCase();
        }

        private static void mostrarResultados(List<Coche> coches, String marcaBuscada) {
            coches.stream()
                    .filter(c -> c.getMarca().equalsIgnoreCase(marcaBuscada))
                    .sorted(Comparator.comparingInt(Coche::getCilindrada))
                    .forEach(c -> System.out.printf("Modelo: %-25s | Cilindrada: %d\n",
                            c.getModelo(), c.getCilindrada()));
        }

        private static int leerEntero() {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                return -1;
            }
        }
    }

