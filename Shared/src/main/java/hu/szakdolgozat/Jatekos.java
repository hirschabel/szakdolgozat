package hu.szakdolgozat;

import lombok.Data;

@Data
public class Jatekos {
    private int id;
    private String name;
    private String password;

    private Pozicio pozicio;

    public Jatekos(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Jatekos(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Jatekos(Pozicio pozicio) {
        this.pozicio = pozicio;
    }

    public Jatekos(String name, Pozicio pozicio) {
        this.name = name;
        this.pozicio = pozicio;
    }

    public String toString() {
        return this.id + " " + this.name + " (" + this.getPozicio().getSorPozicio() + ";" + this.getPozicio().getOszlopPozicio() + ")";
    }
}
