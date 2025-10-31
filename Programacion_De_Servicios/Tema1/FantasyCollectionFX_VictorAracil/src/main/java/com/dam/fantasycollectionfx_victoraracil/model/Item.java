package com.dam.fantasycollectionfx_victoraracil.model;

import java.time.LocalDate;

/**
 * Clase item
 */
public class Item {

    //Atributos
    private String code;
    private String name;
    private String type;
    private String rarity;
    private LocalDate obtainedDate;

    //Constructor con solo el código
    public Item(String code) {
        this.code = code;
    }

    //Constructor completo
    public Item(String code, String name, String type, String rarity, LocalDate obtainedDate) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.rarity = rarity;
        this.obtainedDate = obtainedDate;
    }

    //Getters y setters
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getRarity() {
        return rarity;
    }
    public void setRarity(String rarity) {
        this.rarity = rarity;
    }
    public LocalDate getObtainedDate() {
        return obtainedDate;
    }
    public void setObtainedDate(LocalDate obtainedDate) {
        this.obtainedDate = obtainedDate;
    }

    //toString
    @Override
    public String toString() {
        return name + " (" + rarity + ")";
    }

    //equals y hashCode (comparacion por codigo)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Item item = (Item) obj;
        return code != null && code.equals(item.code);
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }
}
