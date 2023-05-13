package hu.szakdolgozat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FelhasznaloDao {
    private final Connection connection;

    public FelhasznaloDao() {
        this.connection = AdatbazisCsatlakozas.getInstance().getConnection();
    }

    public boolean jatekosLetezik(String felhasznalonev, String jelszo) throws SQLException {
        try (PreparedStatement lekerdezes = connection.prepareStatement("SELECT jelszo FROM felhasznalo WHERE felhasznalonev = ?")) {
            lekerdezes.setString(1, felhasznalonev);
            try (ResultSet eredmeny = lekerdezes.executeQuery()) {
                if (eredmeny.next()) {
                    return eredmeny.getString("jelszo").equals(jelszo);
                }
            }
        }
        return false;
    }
}
