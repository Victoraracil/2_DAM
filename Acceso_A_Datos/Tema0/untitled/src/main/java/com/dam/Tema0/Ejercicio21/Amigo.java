package com.dam.Tema0.Ejercicio21;

public class Amigo {
    private String nombre;
    private int edad;
    private String email;
    private String comentarios;

    public Amigo(String nombre, int edad, String email, String comentarios) {
        this.nombre = nombre;
        this.edad = edad;
        this.email = email;
        this.comentarios = comentarios;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getComentarios() {
        return comentarios;
    }

    public int getEdad() {
        return edad;
    }

    @Override
    public String toString() {
        return nombre + ";" + edad + ";" + email + ";" + comentarios;
    }

    // Método para generar el XML de este amigo
    public String toXML() {
        return "  <amigo>\n" +
                "    <nombre>" + escapeXML(nombre) + "</nombre>\n" +
                "    <edad>" + edad + "</edad>\n" +
                "    <email>" + escapeXML(email) + "</email>\n" +
                "    <comentarios>" + escapeXML(comentarios) + "</comentarios>\n" +
                "  </amigo>\n";
    }

    // Escapa caracteres especiales para XML
    private String escapeXML(String s) {
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
