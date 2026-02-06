package com.dam.DAO;


import com.dam.model.Track;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TrackDAO {

    private final Connection connection;

    public TrackDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Inserta una canción.
     */
    public void insert(Track track) throws SQLException {
        String sql = " INSERT INTO track (title, track_number, duration, album_id, genre_id) VALUES (?, ?, ?, ?, ?) ";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, track.getTitle());
            ps.setInt(2, track.getTrackNumber());
            ps.setString(3, track.getDuration());
            ps.setInt(4, track.getAlbumId());
            ps.setInt(5, track.getGenreId());

            ps.executeUpdate();
        }
    }
}
