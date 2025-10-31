package com.dam.Tema0;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Ejercicio25 {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("coches.xml");

            // Si el fichero no existe, crearlo automáticamente
            if (!xmlFile.exists()) {
                crearFicheroXML(xmlFile);
                System.out.println("Fichero coches.xml creado correctamente.");
            }

            // Crear el parser SAX
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                boolean esMarca = false;
                boolean esModelo = false;
                String marcaActual = "";

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (qName.equalsIgnoreCase("marca")) {
                        esMarca = true;
                    } else if (qName.equalsIgnoreCase("modelo")) {
                        esModelo = true;
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    String contenido = new String(ch, start, length).trim();
                    if (esMarca) {
                        marcaActual = contenido;
                        esMarca = false;
                    } else if (esModelo) {
                        if (marcaActual.equalsIgnoreCase("Seat")) {
                            System.out.println(contenido);
                        }
                        esModelo = false;
                    }
                }
            };

            // Parsear el fichero XML
            saxParser.parse(xmlFile, handler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void crearFicheroXML(File xmlFile) throws Exception {
        try (PrintWriter pw = new PrintWriter(new FileWriter(xmlFile))) {
            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            pw.println("<coches>");

            pw.println("  <coche>");
            pw.println("    <marca>Seat</marca>");
            pw.println("    <modelo>Ibiza</modelo>");
            pw.println("  </coche>");

            pw.println("  <coche>");
            pw.println("    <marca>Seat</marca>");
            pw.println("    <modelo>Leon</modelo>");
            pw.println("  </coche>");

            pw.println("  <coche>");
            pw.println("    <marca>Seat</marca>");
            pw.println("    <modelo>Ateca</modelo>");
            pw.println("  </coche>");

            pw.println("  <coche>");
            pw.println("    <marca>Renault</marca>");
            pw.println("    <modelo>Clio</modelo>");
            pw.println("  </coche>");

            pw.println("  <coche>");
            pw.println("    <marca>BMW</marca>");
            pw.println("    <modelo>Serie 3</modelo>");
            pw.println("  </coche>");

            pw.println("</coches>");
        }
    }
}
