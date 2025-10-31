package com.dam.Tema0;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Ejercicio23 {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("asignaturas.xml");

            // Si no existe, crear el fichero con las asignaturas
            if (!xmlFile.exists()) {
                try (PrintWriter pw = new PrintWriter(new FileWriter(xmlFile))) {
                    pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                    pw.println("<asignaturas>");
                    pw.println("  <asignatura>Programacion movil</asignatura>");
                    pw.println("  <asignatura>Acceso a datos</asignatura>");
                    pw.println("  <asignatura>Interfaces</asignatura>");
                    pw.println("  <asignatura>Digitalizacion</asignatura>");
                    pw.println("  <asignatura>IPE2</asignatura>");
                    pw.println("  <asignatura>Cloud</asignatura>");
                    pw.println("  <asignatura>Proyecto</asignatura>");
                    pw.println("  <asignatura>PSP</asignatura>");
                    pw.println("  <asignatura>SGE</asignatura>");
                    pw.println("  <asignatura>Sostenibilidad</asignatura>");
                    pw.println("</asignaturas>");
                }
                System.out.println("Fichero asignaturas.xml creado correctamente.");
            }

            // Crear el parser SAX
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                boolean esAsignatura = false;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (qName.equalsIgnoreCase("asignatura")) {
                        esAsignatura = true;
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    if (esAsignatura) {
                        String nombre = new String(ch, start, length);
                        System.out.println(nombre);
                        esAsignatura = false;
                    }
                }
            };

            // Parsear el fichero XML
            saxParser.parse(xmlFile, handler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
