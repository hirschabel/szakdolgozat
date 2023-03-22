package hu.szakdolgozat;

import lombok.Data;

@Data
public class Jatekos {
    private int id;
    private String name;
    private Pozicio pozicio;
    private Eszkoztar eszkoztar;
    private Hajo hajo;

    public Jatekos(int id, String name, Pozicio pozicio) { //TODO: id alapján való azonosítás (-> eszköztár lekérdezéshez)
        this.id = id;
        this.name = name;
        this.pozicio = pozicio;
        // TODO inventory lekérés
        // TODO hajó lekérés
    }

    public Jatekos(String name, Pozicio pozicio) {
        this.name = name;
        this.pozicio = pozicio;
        this.eszkoztar = new Eszkoztar(0, 0, 0);
        this.hajo = new Hajo(new Pozicio(this.pozicio.getSorPozicio() - 1, this.pozicio.getOszlopPozicio() - 1), 0);
    }

    public String toString() {
        return this.id + " - " + this.name + " - " + this.getPozicio();
    }
}
