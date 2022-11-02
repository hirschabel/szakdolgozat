package hu.szakdolgozat.dao;

import java.sql.*;

public class JatekosDao {
    private Connection connection = AdatbazisCsatlakozas.getConnection();

    public JatekosDao() { }

    public boolean jatekosLetezik(String felhasznalonev, String jelszo) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT jelszo FROM felhasznalo WHERE felhasznalonev = ?");
        stmt.setString(1, felhasznalonev);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            if (rs.getString("jelszo").equals(jelszo)) {
                return true;
            }
        }
        return false;
    }
}
