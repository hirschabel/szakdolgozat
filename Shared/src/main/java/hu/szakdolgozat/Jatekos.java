package hu.szakdolgozat;

import lombok.Data;

@Data
public class Jatekos {
    private int id;
    private String name;
    private Pozicio pozicio;
    private Inventory eszkoztar;

    public Jatekos(int id, String name, Pozicio pozicio) { //TODO: id alapján való azonosítás (-> eszköztár lekérdezéshez)
        this.id = id;
        this.name = name;
        this.pozicio = pozicio;
    }

    public Jatekos(String name, Pozicio pozicio) {
        this.name = name;
        this.pozicio = pozicio;
    }

    public String toString() {
        return this.id + " - " + this.name + " - " + this.getPozicio();
    }
}
