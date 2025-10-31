package com.dam.Tema0;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class Coche {
    String modelo;
    int cilindrada;

    Coche(String modelo, int cilindrada) {
        this.modelo = modelo;
        this.cilindrada = cilindrada;
    }
}

public class Ejercicio27 {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("coches.xml");

            // Crear fichero si no existe
            if (!xmlFile.exists()) {
                crearFicheroXML(xmlFile);
                System.out.println("Fichero coches.xml creado correctamente.");
            }

            Scanner sc = new Scanner(System.in);
            System.out.print("Introduce la marca de coche a buscar: ");
            String marcaBuscada = sc.nextLine().trim();

            // Parsear XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList listaCoches = doc.getElementsByTagName("coche");
            List<Coche> resultados = new ArrayList<>();

            // Filtrar por marca y recoger modelo y cilindrada
            for (int i = 0; i < listaCoches.getLength(); i++) {
                Element coche = (Element) listaCoches.item(i);
                String marca = coche.getElementsByTagName("marca").item(0).getTextContent();
                if (marca.equalsIgnoreCase(marcaBuscada)) {
                    String modelo = coche.getElementsByTagName("modelo").item(0).getTextContent();
                    int cilindrada = Integer.parseInt(coche.getElementsByTagName("cilindrada").item(0).getTextContent());
                    resultados.add(new Coche(modelo, cilindrada));
                }
            }

            // Ordenar descendentemente por cilindrada
            resultados.sort(Comparator.comparingInt((Coche c) -> c.cilindrada).reversed());

            // Mostrar resultados
            if (resultados.isEmpty()) {
                System.out.println("No se encontraron coches de la marca " + marcaBuscada);
            } else {
                System.out.println("Modelos de " + marcaBuscada + " ordenados por cilindrada:");
                for (Coche c : resultados) {
                    System.out.println(c.modelo + " - " + c.cilindrada + " cc");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void crearFicheroXML(File xmlFile) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element root = doc.createElement("coches");
        doc.appendChild(root);

        agregarCoche(doc, root, "Seat", "Ibiza", 1200);
        agregarCoche(doc, root, "Seat", "Leon", 1600);
        agregarCoche(doc, root, "Seat", "Ateca", 2000);
        agregarCoche(doc, root, "Renault", "Clio", 1400);
        agregarCoche(doc, root, "BMW", "Serie 3", 2000);
        agregarCoche(doc, root, "Ford", "Focus", 1800);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(xmlFile);
        transformer.transform(source, result);
    }

    private static void agregarCoche(Document doc, Element root, String marca, String modelo, int cilindrada) {
        Element coche = doc.createElement("coche");

        Element eMarca = doc.createElement("marca");
        eMarca.appendChild(doc.createTextNode(marca));
        coche.appendChild(eMarca);

        Element eModelo = doc.createElement("modelo");
        eModelo.appendChild(doc.createTextNode(modelo));
        coche.appendChild(eModelo);

        Element eCilindrada = doc.createElement("cilindrada");
        eCilindrada.appendChild(doc.createTextNode(String.valueOf(cilindrada)));
        coche.appendChild(eCilindrada);

        root.appendChild(coche);
    }
}
