package com.dam.model;

public class Track {

    private int id;
    private String title;
    private int trackNumber;
    private String duration;
    private int albumId; // FK
    private int genreId; // FK

    public Track() {
    }

    public Track(String title, int trackNumber, String duration, int albumId, int genreId) {
        this.title = title;
        this.trackNumber = trackNumber;
        this.duration = duration;
        this.albumId = albumId;
        this.genreId = genreId;
    }

    public Track(int id, String title, int trackNumber, String duration, int albumId, int genreId) {
        this.id = id;
        this.title = title;
        this.trackNumber = trackNumber;
        this.duration = duration;
        this.albumId = albumId;
        this.genreId = genreId;
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

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }
}
