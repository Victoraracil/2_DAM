package com.dam.Tema0.Ejercicio28;

import java.io.Serializable;
import java.util.List;

public class Personaje implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String height;
    private String mass;
    private String hair_color;
    private String skin_color;
    private String eye_color;
    private String birth_year;
    private String gender;
    private List<String> speciesUrls; // guardamos URLs de especies por si queremos consultarlas después

    public Personaje() {}

    public Personaje(String name, String height, String mass, String hair_color,
                     String skin_color, String eye_color, String birth_year,
                     String gender, List<String> speciesUrls) {
        this.name = name;
        this.height = height;
        this.mass = mass;
        this.hair_color = hair_color;
        this.skin_color = skin_color;
        this.eye_color = eye_color;
        this.birth_year = birth_year;
        this.gender = gender;
        this.speciesUrls = speciesUrls;
    }

    // getters y setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getHeight() { return height; }
    public void setHeight(String height) { this.height = height; }
    public String getMass() { return mass; }
    public void setMass(String mass) { this.mass = mass; }
    public String getHair_color() { return hair_color; }
    public void setHair_color(String hair_color) { this.hair_color = hair_color; }
    public String getSkin_color() { return skin_color; }
    public void setSkin_color(String skin_color) { this.skin_color = skin_color; }
    public String getEye_color() { return eye_color; }
    public void setEye_color(String eye_color) { this.eye_color = eye_color; }
    public String getBirth_year() { return birth_year; }
    public void setBirth_year(String birth_year) { this.birth_year = birth_year; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public List<String> getSpeciesUrls() { return speciesUrls; }
    public void setSpeciesUrls(List<String> speciesUrls) { this.speciesUrls = speciesUrls; }

    @Override
    public String toString() {
        return "Personaje{" +
                "name='" + name + '\'' +
                ", height='" + height + '\'' +
                ", mass='" + mass + '\'' +
                ", hair_color='" + hair_color + '\'' +
                ", skin_color='" + skin_color + '\'' +
                ", eye_color='" + eye_color + '\'' +
                ", birth_year='" + birth_year + '\'' +
                ", gender='" + gender + '\'' +
                ", speciesUrls=" + speciesUrls +
                '}';
    }
}

