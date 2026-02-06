package com.dam.DAO;

import com.dam.model.Album;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlbumDAO {

    private final Connection connection;

    public AlbumDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Busca un álbum por título y artista.
     *
     * @return el id del álbum si existe, o -1 si no existe
     */
    public int findByTitleAndArtist(String title, int artistId) throws SQLException {
        String sql = "SELECT id FROM album WHERE title = ? AND artist_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setInt(2, artistId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return -1;
    }

    /**
     * Inserta un álbum y devuelve el ID generado.
     */
    public int insert(Album album) throws SQLException {
        String sql = "INSERT INTO album (title, year, artist_id) VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, album.getTitle());
            ps.setInt(2, album.getYear());
            ps.setInt(3, album.getArtistId());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        throw new SQLException("No se pudo insertar el álbum");
    }
}
