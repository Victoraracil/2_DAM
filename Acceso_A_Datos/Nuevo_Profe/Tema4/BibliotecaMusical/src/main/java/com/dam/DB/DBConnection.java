package com.dam.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/Music_Library";
    private static final String USER = "postgres";   //ajustar si hace falta
    private static final String PASSWORD = "1234"; //ajustar si hace falta

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
