package com.dam;


import com.dam.DB.DBConnection;
import com.dam.service.MusicImportService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {

        if (args.length < 1) {
            printUsage();
            return;
        }

        try {
            switch (args[0]) {

                case "-I":
                    if (args.length != 2) {
                        printUsage();
                        return;
                    }
                    MusicImportService service = new MusicImportService();
                    service.importFromJson(args[1]);
                    System.out.println("Importación finalizada correctamente");
                    break;

                case "-L":
                    if (args.length != 2) {
                        printUsage();
                        return;
                    }
                    listData(args[1]);
                    break;

                default:
                    printUsage();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //LISTADOS

    private static void listData(String option) throws Exception {

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement()) {

            String sql;

            switch (option) {
                case "-C":
                    sql = "SELECT title FROM track ORDER BY title";
                    break;
                case "-A":
                    sql = "SELECT name FROM artist ORDER BY name";
                    break;
                case "-B":
                    sql = "SELECT title FROM album ORDER BY title";
                    break;
                case "-G":
                    sql = "SELECT name FROM genre ORDER BY name";
                    break;
                default:
                    printUsage();
                    return;
            }

            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        }
    }

    private static void printUsage() {
        System.out.println("  + \n" +
                "Uso: \n" +
                "-I <ruta_json>       Importar canciones \n" +
                "-L -C                Listar canciones \n" +
                "-L -A                Listar artistas \n" +
                "-L -B                Listar álbumes \n" +
                "-L -G                Listar géneros \n");
    }
}
