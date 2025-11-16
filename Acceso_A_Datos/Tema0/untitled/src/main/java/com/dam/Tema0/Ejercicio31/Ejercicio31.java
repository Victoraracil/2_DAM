package com.dam.Tema0.Ejercicio31;

import com.google.gson.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class Ejercicio31 {
    public static void main(String[] args) {
        String url = "https://jsonplaceholder.typicode.com/todos";
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();

        try {
            //Conectamos con la URL
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Comprobamos si la conexión fue exitosa
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                System.out.println("Error al conectar: código " + responseCode);
                return;
            }

            //Leemos la respuesta en un String
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            reader.close();
            conn.disconnect();

            //Parseamos el JSON usando GSON
            Gson gson = new Gson();
            JsonArray jsonArray = JsonParser.parseString(jsonBuilder.toString()).getAsJsonArray();

            //Recorremos y filtramos los objetos
            for (JsonElement elem : jsonArray) {
                JsonObject obj = elem.getAsJsonObject();

                int userId = obj.get("userId").getAsInt();
                int id = obj.get("id").getAsInt();
                String title = obj.get("title").getAsString();
                boolean completed = obj.get("completed").getAsBoolean();

                //Filtramos: userId par y completed = true
                if (userId % 2 == 0 && completed) {
                    listaUsuarios.add(new Usuario(userId, id, title, completed));
                }
            }

            //Mostramos resultados
            System.out.println("=== RESULTADOS FILTRADOS ===");
            listaUsuarios.forEach(System.out::println);

            //Serializamos el ArrayList a fichero binario
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("usuarios_filtrados.dat"))) {
                oos.writeObject(listaUsuarios);
                System.out.println("\nDatos serializados correctamente en 'usuarios_filtrados.dat'");
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

