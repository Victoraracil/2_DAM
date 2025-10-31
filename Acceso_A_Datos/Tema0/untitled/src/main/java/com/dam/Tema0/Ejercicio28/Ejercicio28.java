package com.dam.Tema0.Ejercicio28;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ejercicio28 {

    private static final String BASE = "https://swapi.dev/api/";
    private static final String PERSONAJES_DAT = "personajes.dat";
    private static final String RESULT_XML = "resultado.xml";

    private List<Personaje> personajes = new ArrayList<>();

    public static void main(String[] args) {
        new Ejercicio28().run();
    }

    private void run() {
        // al iniciar, desserializar personajes.dat
        loadPersonajes();

        Scanner sc = new Scanner(System.in);
        boolean salir = false;
        while (!salir) {
            printMenu();
            System.out.print("Elige opción: ");
            String opt = sc.nextLine().trim();
            try {
                switch (opt) {
                    case "1":
                        opcionConversor(sc);
                        break;
                    case "2":
                        opcionAnadirPersonaje(sc);
                        break;
                    case "3":
                        opcionSalvarPersonajes();
                        break;
                    case "4":
                        opcionEspeciePersonaje(sc);
                        break;
                    case "5":
                        opcionMostrarDatosXml();
                        break;
                    case "6":
                        System.out.println("Saliendo...");
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace(System.out);
            }
        }
        sc.close();
    }

    private void printMenu() {
        System.out.println("\n--- MENÚ ---");
        System.out.println("1. Conversor (film JSON -> resultado.xml)");
        System.out.println("2. Añadir Personaje");
        System.out.println("3. Salvar Personajes (serialización)");
        System.out.println("4. Especie del Personaje");
        System.out.println("5. Mostrar datos XML (resultado.xml)");
        System.out.println("6. Salir");
    }

    // OPCIÓN 1
    private void opcionConversor(Scanner sc) throws Exception {
        System.out.print("Introduce código de película (id): ");
        String id = sc.nextLine().trim();
        String url = BASE + "films/" + id + "/?format=json";
        System.out.println("Llamando a: " + url);
        JsonElement je = ApiHelper.getJsonFromUrl(url);
        if (!je.isJsonObject()) {
            System.out.println("Respuesta inesperada.");
            return;
        }
        JsonObject film = je.getAsJsonObject();
        // convertir y guardar resultado.xml
        JsonToXmlConverter.filmJsonToXml(film, RESULT_XML);
        System.out.println("Generado " + RESULT_XML);
    }

    // OPCIÓN 2
    private void opcionAnadirPersonaje(Scanner sc) throws Exception {
        System.out.print("Introduce código de personaje (id): ");
        String id = sc.nextLine().trim();
        String url = BASE + "people/" + id + "/?format=json";
        System.out.println("Llamando a: " + url);
        JsonElement je = ApiHelper.getJsonFromUrl(url);
        if (!je.isJsonObject()) {
            System.out.println("Respuesta inesperada.");
            return;
        }
        JsonObject p = je.getAsJsonObject();
        String name = p.has("name") ? p.get("name").getAsString() : "Unknown";

        // comprobar duplicado por nombre
        boolean exists = personajes.stream().anyMatch(pp -> pp.getName().equalsIgnoreCase(name));
        if (exists) {
            System.out.println("El personaje ya existe en la lista: " + name);
            return;
        }

        // leer campos
        String height = p.has("height") ? p.get("height").getAsString() : "";
        String mass = p.has("mass") ? p.get("mass").getAsString() : "";
        String hair_color = p.has("hair_color") ? p.get("hair_color").getAsString() : "";
        String skin_color = p.has("skin_color") ? p.get("skin_color").getAsString() : "";
        String eye_color = p.has("eye_color") ? p.get("eye_color").getAsString() : "";
        String birth_year = p.has("birth_year") ? p.get("birth_year").getAsString() : "";
        String gender = p.has("gender") ? p.get("gender").getAsString() : "";
        List<String> speciesUrls = new ArrayList<>();
        if (p.has("species") && p.get("species").isJsonArray()) {
            JsonArray arr = p.get("species").getAsJsonArray();
            for (JsonElement s : arr) {
                speciesUrls.add(s.getAsString());
            }
        }

        Personaje nuevo = new Personaje(name, height, mass, hair_color, skin_color, eye_color, birth_year, gender, speciesUrls);
        personajes.add(nuevo);
        System.out.println("Añadido personaje: " + name);
    }

    // OPCIÓN 3
    private void opcionSalvarPersonajes() {
        // serializar lista a personajes.dat
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(PERSONAJES_DAT));
            oos.writeObject(personajes);
            System.out.println("Personajes salvados en " + PERSONAJES_DAT);
        } catch (IOException e) {
            System.out.println("Error guardando personajes: " + e.getMessage());
        } finally {
            ApiHelper.closeQuietly(oos);
        }
    }

    // OPCIÓN 4
    private void opcionEspeciePersonaje(Scanner sc) throws Exception {
        System.out.print("Introduce código del personaje (id): ");
        String id = sc.nextLine().trim();
        String url = BASE + "people/" + id + "/?format=json";
        JsonElement je = ApiHelper.getJsonFromUrl(url);
        if (!je.isJsonObject()) {
            System.out.println("Respuesta inesperada.");
            return;
        }
        JsonObject p = je.getAsJsonObject();
        if (!p.has("species") || !p.get("species").isJsonArray() || p.get("species").getAsJsonArray().size() == 0) {
            System.out.println("El personaje no pertenece a ninguna especie (o no definida en SWAPI).");
            return;
        }
        JsonArray arr = p.get("species").getAsJsonArray();
        System.out.println("Especies del personaje:");
        for (JsonElement s : arr) {
            String speciesUrl = s.getAsString();
            System.out.println(" -> Consultando " + speciesUrl);
            JsonElement specJe = ApiHelper.getJsonFromUrl(speciesUrl + "?format=json");
            if (specJe.isJsonObject()) {
                JsonObject specObj = specJe.getAsJsonObject();
                String speciesName = specObj.has("name") ? specObj.get("name").getAsString() : "(sin nombre)";
                System.out.println("    Nombre especie: " + speciesName);
            } else {
                System.out.println("    No se pudo obtener la especie desde: " + speciesUrl);
            }
        }
    }

    // OPCIÓN 5
    private void opcionMostrarDatosXml() {
        File xml = new File(RESULT_XML);
        if (!xml.exists()) {
            System.out.println("No existe " + RESULT_XML + ". Primero usa la opción 1 para crearla.");
            return;
        }
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xml);
            doc.getDocumentElement().normalize();
            Element root = doc.getDocumentElement();
            System.out.println("Datos de la película (desde " + RESULT_XML + "):");
            // imprimimos el contenido de las etiquetas hijas directas
            NodeList children = root.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node n = children.item(i);
                if (n.getNodeType() != Node.ELEMENT_NODE) continue;
                Element el = (Element) n;
                String tag = el.getTagName();
                // si es un nodo con elementos <item> (arrays), imprimimos lista
                NodeList items = el.getElementsByTagName("item");
                if (items != null && items.getLength() > 0) {
                    System.out.println(tag + ":");
                    for (int j = 0; j < items.getLength(); j++) {
                        Node item = items.item(j);
                        System.out.println("  - " + item.getTextContent());
                    }
                } else {
                    System.out.println(tag + ": " + el.getTextContent());
                }
            }
        } catch (Exception e) {
            System.out.println("Error leyendo XML: " + e.getMessage());
            e.printStackTrace(System.out);
        }
    }

    // carga personajes desde personajes.dat al iniciar
    private void loadPersonajes() {
        File f = new File(PERSONAJES_DAT);
        if (!f.exists()) {
            System.out.println("No hay fichero de personajes previo.");
            return;
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(f));
            Object obj = ois.readObject();
            if (obj instanceof List) {
                List<?> list = (List<?>) obj;
                for (Object o : list) {
                    if (o instanceof Personaje) personajes.add((Personaje) o);
                }
                System.out.println("Cargados " + personajes.size() + " personajes desde " + PERSONAJES_DAT);
            } else {
                System.out.println("Contenido de " + PERSONAJES_DAT + " no es una lista válida.");
            }
        } catch (Exception e) {
            System.out.println("Error cargando personajes: " + e.getMessage());
        } finally {
            ApiHelper.closeQuietly(ois);
        }
    }
}
