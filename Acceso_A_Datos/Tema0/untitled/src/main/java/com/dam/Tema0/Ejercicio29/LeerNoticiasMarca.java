package com.dam.Tema0.Ejercicio29;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;

public class LeerNoticiasMarca {

    private static final String FICHERO_SERIAL = "noticias_marca.ser";

    public static void main(String[] args) {
        ArrayList<Noticia> todasNoticias = new ArrayList<>();

        // Paso 6: comprobar si existe fichero serializado
        File serializado = new File(FICHERO_SERIAL);
        if (serializado.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(serializado))) {
                todasNoticias = (ArrayList<Noticia>) ois.readObject();
                System.out.println("Cargadas " + todasNoticias.size() + " noticias desde fichero serializado.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Paso 2: obtener URLs de RSS
        ArrayList<String> urls = MarcaRSS.obtenerXmls();

        // Paso 3 y 4: leer cada RSS y añadir noticias si no existen por GUID
        for (String urlStr : urls) {
            try {
                URL url = new URL(urlStr);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(url.openStream());
                doc.getDocumentElement().normalize();

                NodeList items = doc.getElementsByTagName("item");
                for (int i = 0; i < items.getLength(); i++) {
                    Element item = (Element) items.item(i);
                    String guid = getTagValue(item, "guid");

                    boolean existe = todasNoticias.stream().anyMatch(n -> n.getGuid().equals(guid));
                    if (!existe) {
                        Noticia n = new Noticia();
                        n.setGuid(guid);
                        n.setTitle(getTagValue(item, "title"));
                        n.setLink(getTagValue(item, "link"));
                        n.setDescription(getTagValue(item, "description"));
                        n.setPubDate(getTagValue(item, "pubDate"));

                        // Categorias
                        NodeList cats = item.getElementsByTagName("category");
                        ArrayList<String> categorias = new ArrayList<>();
                        for (int j = 0; j < cats.getLength(); j++) {
                            categorias.add(cats.item(j).getTextContent());
                        }
                        n.setCategorias(categorias);

                        todasNoticias.add(n);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error leyendo URL: " + urlStr);
                e.printStackTrace();
            }
        }

        System.out.println("Total de noticias recopiladas: " + todasNoticias.size());

        // Paso 5: serializar las noticias
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHERO_SERIAL))) {
            oos.writeObject(todasNoticias);
            System.out.println("Noticias guardadas en " + FICHERO_SERIAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getTagValue(Element item, String tag) {
        NodeList list = item.getElementsByTagName(tag);
        if (list.getLength() > 0) {
            return list.item(0).getTextContent();
        }
        return "";
    }
}

