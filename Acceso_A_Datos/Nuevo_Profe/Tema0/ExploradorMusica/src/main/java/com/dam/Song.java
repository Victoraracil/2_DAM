package com.dam;

import java.io.Serializable;

/**
 * Representa una canción MP3 con metadatos ID3v1.
 * Esta clase se serializa para almacenar la información
 * en un fichero binario.
 */
public class Song implements Serializable {

    private static final long serialVersionUID = 1L;

    // Ruta completa del archivo MP3
    private String filePath;

    // Metadatos ID3v1
    private String title;
    private String artist;
    private String album;
    private String year;
    private String comment;
    private int genre;

    public Song(String filePath, String title, String artist,
                String album, String year, String comment, int genre) {
        this.filePath = filePath;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.year = year;
        this.comment = comment;
        this.genre = genre;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getYear() {
        return year;
    }

    public String getComment() {
        return comment;
    }

    public int getGenre() {
        return genre;
    }

    /**
     * Representación legible de la canción para el modo -L
     */
    @Override
    public String toString() {
        return """
                Ruta: %s
                Título: %s
                Artista: %s
                Álbum: %s
                Año: %s
                Comentario: %s
                Género (código): %d
                ---------------------------------------
                """.formatted(
                filePath,
                title,
                artist,
                album,
                year,
                comment,
                genre
        );
    }
}
