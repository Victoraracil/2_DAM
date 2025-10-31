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

public class Ejercicio24 {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("asignaturas.xml");

            // Si el fichero no existe, crear uno con las asignaturas
            if (!xmlFile.exists()) {
                crearFicheroXML(xmlFile);
                System.out.println("Fichero asignaturas.xml creado correctamente.");
            }

            // Crear DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            // Normalizar el documento
            doc.getDocumentElement().normalize();

            // Obtener todos los elementos <asignatura>
            NodeList listaAsignaturas = doc.getElementsByTagName("asignatura");

            System.out.println("Asignaturas de segundo curso:");
            for (int i = 0; i < listaAsignaturas.getLength(); i++) {
                Element asignatura = (Element) listaAsignaturas.item(i);
                String nombre = asignatura.getTextContent();
                System.out.println(nombre);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void crearFicheroXML(File xmlFile) throws Exception {
        String[] asignaturas = {
                "Programacion movil", "Acceso a datos", "Interfaces",
                "Digitalizacion", "IPE2", "Cloud", "Proyecto",
                "PSP", "SGE", "Sostenibilidad"
        };

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        // Crear elemento raíz <asignaturas>
        Element root = doc.createElement("asignaturas");
        doc.appendChild(root);

        // Crear elementos <asignatura>
        for (String nombre : asignaturas) {
            Element e = doc.createElement("asignatura");
            e.appendChild(doc.createTextNode(nombre));
            root.appendChild(e);
        }

        // Escribir XML en el fichero
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(xmlFile);
        transformer.transform(source, result);
    }
}
