package hu.szakdolgozat;

import lombok.Data;
import lombok.Getter;

@Data
public class Jatekos {
    private int id;
    private String name;
    private String password;

    public Jatekos() {}

    public Jatekos(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public boolean megegyezik(String felhasznaloNev, String jelszo) {
        return this.name.equals(felhasznaloNev) && this.password.equals(jelszo);
    }
}
