package com.dam.DAO;

import com.dam.model.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class GenreDAO {

    private final Connection connection;

    public GenreDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Busca un género por nombre.
     *
     * @return el id del género si existe, o -1 si no existe
     */
    public int findByName(String name) throws SQLException {
        String sql = "SELECT id FROM genre WHERE name = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return -1;
    }

    /**
     * Inserta un nuevo género y devuelve el ID generado.
     */
    public int insert(Genre genre) throws SQLException {
        String sql = "INSERT INTO genre (name) VALUES (?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, genre.getName());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        throw new SQLException("No se pudo insertar el género");
    }
}
