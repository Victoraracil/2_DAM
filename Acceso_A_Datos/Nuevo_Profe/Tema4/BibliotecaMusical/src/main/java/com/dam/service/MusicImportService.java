package com.dam.service;


import com.dam.DAO.AlbumDAO;
import com.dam.DAO.ArtistDAO;
import com.dam.DAO.GenreDAO;
import com.dam.DAO.TrackDAO;
import com.dam.DB.DBConnection;
import com.dam.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public class MusicImportService {

    public void importFromJson(String jsonPath) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        JsonTrack[] tracks = mapper.readValue(new File(jsonPath), JsonTrack[].class);

        try (Connection conn = DBConnection.getConnection()) {

            GenreDAO genreDAO = new GenreDAO(conn);
            ArtistDAO artistDAO = new ArtistDAO(conn);
            AlbumDAO albumDAO = new AlbumDAO(conn);
            TrackDAO trackDAO = new TrackDAO(conn);

            for (JsonTrack jt : tracks) {

                try {
                    conn.setAutoCommit(false); //empieza transacción

                    //Género
                    int genreId = genreDAO.findByName(jt.getGenre());
                    if (genreId == -1) {
                        genreId = genreDAO.insert(new Genre(jt.getGenre()));
                    }

                    //Artista
                    int artistId = artistDAO.findByName(jt.getArtist());
                    if (artistId == -1) {
                        artistId = artistDAO.insert(new Artist(jt.getArtist()));
                    }

                    //Álbum
                    int albumId = albumDAO.findByTitleAndArtist(jt.getAlbum(), artistId);
                    if (albumId == -1) {
                        albumId = albumDAO.insert(new Album(jt.getAlbum(), jt.getYear(), artistId));
                    }

                    //Canción
                    Track track = new Track(jt.getTitle(), jt.getTrack_number(), jt.getDuration(), albumId, genreId);

                    trackDAO.insert(track);

                    conn.commit(); //todo OK

                } catch (SQLException e) {
                    conn.rollback(); //algo falló
                    System.err.println("Error importando canción: " + jt.getTitle());
                    e.printStackTrace();
                }
            }
        }
    }
}

