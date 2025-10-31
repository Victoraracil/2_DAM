package com.dam.Tema0.Ejercicio29;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MarcaRSS {

    public static ArrayList<String> obtenerXmls() {
        ArrayList<String> urls = new ArrayList<>();
        try {
            URL url = new URL("https://www.marca.com/rss.html");
            URLConnection uc = url.openConnection();
            uc.connect();

            List<String> lines = new BufferedReader(new InputStreamReader(uc.getInputStream(), StandardCharsets.UTF_8))
                    .lines()
                    .filter(line -> line.contains(".xml") && !line.contains("link"))
                    .map(t -> {
                        int inicio = t.indexOf("href");
                        int fin = t.indexOf("xml");
                        return t.substring(inicio + 6, fin + 3);
                    })
                    .collect(Collectors.toList());

            urls.addAll(lines);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urls;
    }
}
