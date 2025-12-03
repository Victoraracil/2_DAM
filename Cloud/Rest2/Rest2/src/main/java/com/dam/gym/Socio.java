package com.dam.gym;

public class Socio {
    private String id;
    private String nombre;
    private String email;
    public Socio(String id, String nombre, String email) {
        this.id = id; this.nombre = nombre; this.email = email;
    }
    //getters y setters
    public String getId(){ return id; }
    public String getNombre(){ return nombre; }
    public String getEmail(){ return email; }
    public void setId(String id){ this.id = id; }
}
