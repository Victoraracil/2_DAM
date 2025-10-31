package com.dam.Tema0.Ejercicio28;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;

public class JsonToXmlConverter {

    /**
     * Convierte el JsonObject de una film a un documento XML y lo guarda en resultado.xml
     */
    public static void filmJsonToXml(JsonObject filmJson, String outputPath) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        // root
        Element rootElement = doc.createElement("film");
        doc.appendChild(rootElement);

        // recorremos las entradas del objeto JSON y creamos nodos adecuados
        for (Map.Entry<String, JsonElement> entry : filmJson.entrySet()) {
            String key = entry.getKey();
            JsonElement value = entry.getValue();

            // omitimos fields que sean blank por seguridad (pero no es obligatorio)
            if (value.isJsonNull()) continue;

            if (value.isJsonPrimitive()) {
                Element el = doc.createElement(sanitizeXmlTag(key));
                el.appendChild(doc.createTextNode(value.getAsString()));
                rootElement.appendChild(el);
            } else if (value.isJsonArray()) {
                // crear un nodo padre y luego elementos item
                Element parent = doc.createElement(sanitizeXmlTag(key));
                JsonArray arr = value.getAsJsonArray();
                for (JsonElement je : arr) {
                    Element item = doc.createElement("item");
                    if (je.isJsonPrimitive()) {
                        item.appendChild(doc.createTextNode(je.getAsString()));
                    } else {
                        // si fuera objeto, convertimos sus campos
                        if (je.isJsonObject()) {
                            JsonObject obj = je.getAsJsonObject();
                            for (Map.Entry<String, JsonElement> sub : obj.entrySet()) {
                                Element subEl = doc.createElement(sanitizeXmlTag(sub.getKey()));
                                subEl.appendChild(doc.createTextNode(sub.getValue().getAsString()));
                                item.appendChild(subEl);
                            }
                        } else {
                            item.appendChild(doc.createTextNode(je.toString()));
                        }
                    }
                    parent.appendChild(item);
                }
                rootElement.appendChild(parent);
            } else if (value.isJsonObject()) {
                Element parent = doc.createElement(sanitizeXmlTag(key));
                JsonObject obj = value.getAsJsonObject();
                for (Map.Entry<String, JsonElement> sub : obj.entrySet()) {
                    Element subEl = doc.createElement(sanitizeXmlTag(sub.getKey()));
                    subEl.appendChild(doc.createTextNode(sub.getValue().toString()));
                    parent.appendChild(subEl);
                }
                rootElement.appendChild(parent);
            } else {
                // fallback
                Element el = doc.createElement(sanitizeXmlTag(key));
                el.appendChild(doc.createTextNode(value.toString()));
                rootElement.appendChild(el);
            }
        }

        // escribir documento a archivo
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource domSource = new DOMSource(doc);
        File out = new File(outputPath);
        out.getParentFile().mkdirs();
        try (OutputStream os = new FileOutputStream(out)) {
            StreamResult streamResult = new StreamResult(os);
            transformer.transform(domSource, streamResult);
        }
    }

    private static String sanitizeXmlTag(String tag) {
        // nombres de etiquetas válidos: no empezar por número, etc.
        if (tag == null || tag.isEmpty()) return "field";
        String t = tag.replaceAll("[^A-Za-z0-9_\\-\\.]", "_");
        if (Character.isDigit(t.charAt(0))) {
            t = "_" + t;
        }
        return t;
    }
}
