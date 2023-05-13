package hu.szakdolgozat.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AdatbazisCsatlakozas {
    private static final String URL = "jdbc:postgresql://localhost:5432/szakdolgozat";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";
    private static Connection con;
    private static AdatbazisCsatlakozas instance;

    private AdatbazisCsatlakozas() {
        try {
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static AdatbazisCsatlakozas getInstance() {
        if (instance == null) {
            instance = new AdatbazisCsatlakozas();
        }
        return instance;
    }

    public Connection getConnection() {
        return con;
    }
}
