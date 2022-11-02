package hu.szakdolgozat;

import lombok.Data;

@Data
public class Jatekos {
    private int id;
    private String name;
    private String password;

    public Jatekos(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Jatekos(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
