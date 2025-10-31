package com.dam.Tema0.Ejercicio28;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ApiHelper {
    private static final Gson gson = new Gson();

    public static JsonElement getJsonFromUrl(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);

        int status = conn.getResponseCode();
        InputStream is = (status >= 200 && status < 400) ? conn.getInputStream() : conn.getErrorStream();

        try (InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return JsonParser.parseString(sb.toString());
        } finally {
            conn.disconnect();
        }
    }

    public static String getStringFromUrl(String urlStr) throws IOException {
        return getJsonFromUrl(urlStr).toString();
    }

    public static Gson getGson() {
        return gson;
    }

    // util: safe close for object streams
    public static void closeQuietly(Closeable c) {
        try { if (c != null) c.close(); } catch (IOException ignored) {}
    }
}
