package hu.szakdolgozat.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class AdatbazisCsatlakozas {
    private String felhasznaloNev = "admin";
    private String jelszo = "admin";
    private static Connection con;
    private static String url = "jdbc:postgresql://localhost:5433/szakdolgozat";

    public static Connection getConnection() {
        try {
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", "postgres");
            con = DriverManager.getConnection(url, props);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return con;
    }
}
