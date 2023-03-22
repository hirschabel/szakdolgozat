package hu.szakdolgozat.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AdatbazisCsatlakozas {
    private static Connection con;
    private final static String url = "jdbc:sqlite:./Shared/src/main/resources/szakdolgozat.db";

    public static Connection getConnection() {
        try {
            con = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return con;
    }
}
