package com.dam.Tema0;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class Ejercicio26 {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("coches.xml");

            // Si no existe, crear el fichero con algunos coches
            if (!xmlFile.exists()) {
                crearFicheroXML(xmlFile);
                System.out.println("Fichero coches.xml creado correctamente.");
            }

            // Crear DocumentBuilder y parsear XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            // Normalizar el documento
            doc.getDocumentElement().normalize();

            // Obtener todos los elementos <coche>
            NodeList listaCoches = doc.getElementsByTagName("coche");

            System.out.println("Modelos de coches marca Seat:");
            for (int i = 0; i < listaCoches.getLength(); i++) {
                Element coche = (Element) listaCoches.item(i);
                String marca = coche.getElementsByTagName("marca").item(0).getTextContent();
                if (marca.equalsIgnoreCase("Seat")) {
                    String modelo = coche.getElementsByTagName("modelo").item(0).getTextContent();
                    System.out.println(modelo);
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

        // Crear elemento raíz <coches>
        Element root = doc.createElement("coches");
        doc.appendChild(root);

        // Crear algunos coches
        agregarCoche(doc, root, "Seat", "Ibiza");
        agregarCoche(doc, root, "Seat", "Leon");
        agregarCoche(doc, root, "Seat", "Ateca");
        agregarCoche(doc, root, "Renault", "Clio");
        agregarCoche(doc, root, "BMW", "Serie 3");

        // Escribir XML en el fichero
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(xmlFile);
        transformer.transform(source, result);
    }

    private static void agregarCoche(Document doc, Element root, String marca, String modelo) {
        Element coche = doc.createElement("coche");

        Element eMarca = doc.createElement("marca");
        eMarca.appendChild(doc.createTextNode(marca));
        coche.appendChild(eMarca);

        Element eModelo = doc.createElement("modelo");
        eModelo.appendChild(doc.createTextNode(modelo));
        coche.appendChild(eModelo);

        root.appendChild(coche);
    }
}
