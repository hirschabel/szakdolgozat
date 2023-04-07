package hu.szakdolgozat;

import hu.szakdolgozat.hajo.Hajo;
import lombok.Data;

@Data
public class Jatekos {
    private int id;
    private String name;
    private Pozicio pozicio;
    private Eszkoztar eszkoztar;
    private Hajo hajo;
    private Eroforras eroforrasok;

// TODO OBJECT RELATIONAL DATABASE
    public Jatekos(int id, String name, Pozicio pozicio) { //TODO: id alapján való azonosítás (-> eszköztár lekérdezéshez)
        this.id = id;
        this.name = name;
        this.pozicio = pozicio;
        // TODO inventory lekérés
        // TODO hajó lekérés
        // TODO max élet ital lekérdezés
    }

    public Jatekos(String name, Pozicio pozicio) {
        this.name = name;
        this.pozicio = pozicio;
        this.eszkoztar = new Eszkoztar(0, 0, 0);
        this.hajo = new Hajo(new Pozicio(this.pozicio.getSorPozicio() - 1, this.pozicio.getOszlopPozicio() - 1), 0);
        this.eroforrasok = new Eroforras(100, 100, 100); // Default erőforrások
    }

    public String toString() {
        return this.id + " - " + this.name + " - " + this.getPozicio();
    }

    public boolean tudVisszatolteni() {
        return this.hajo.getSzintAdat().tudVisszatolteni();
    }

    public void visszatolt() {
        if (this.hajo.getSzintAdat().isEtelVisszatoltes()) {
            this.eroforrasok.etelVisszatoltes();
        }
        if (this.hajo.getSzintAdat().isItalVisszatoltes()) {
            this.eroforrasok.italVisszatoltes();
        }
    }
}
