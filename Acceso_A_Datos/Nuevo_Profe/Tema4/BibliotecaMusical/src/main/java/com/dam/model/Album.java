package com.dam.model;

public class Album {

    private int id;
    private String title;
    private int year;
    private int artistId; // FK

    public Album() {
    }

    public Album(String title, int year, int artistId) {
        this.title = title;
        this.year = year;
        this.artistId = artistId;
    }

    public Album(int id, String title, int year, int artistId) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.artistId = artistId;
    }

    // getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }
}
