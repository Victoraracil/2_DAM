package com.dam.Tema0.Ejercicio29;

import java.io.Serializable;
import java.util.ArrayList;

public class Noticia implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String link;
    private String description;
    private String guid;
    private String pubDate;
    private ArrayList<String> categorias;

    public Noticia() {
        categorias = new ArrayList<>();
    }

    // Getters y setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getGuid() { return guid; }
    public void setGuid(String guid) { this.guid = guid; }

    public String getPubDate() { return pubDate; }
    public void setPubDate(String pubDate) { this.pubDate = pubDate; }

    public ArrayList<String> getCategorias() { return categorias; }
    public void setCategorias(ArrayList<String> categorias) { this.categorias = categorias; }
}
