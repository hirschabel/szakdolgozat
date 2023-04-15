package hu.szakdolgozat;

import hu.szakdolgozat.hajo.Hajo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Jatekos {
    @Id
    private String name;

    @Embedded
    private Pozicio pozicio;

    @Embedded
    private Eszkoztar eszkoztar;

    @OneToOne(cascade = CascadeType.ALL)
    private Hajo hajo;

    @Embedded
    private Eroforras eroforrasok;

    public Jatekos(String name, Pozicio pozicio) {
        this.name = name;
        this.pozicio = pozicio;
        this.eszkoztar = new Eszkoztar(0, 0, 0);
        this.hajo = new Hajo(new Pozicio(this.pozicio.getSorPozicio() - 1, this.pozicio.getOszlopPozicio() - 1), 0);
        this.eroforrasok = new Eroforras(100, 100, 100);
    }

    public String toString() {
        return this.name + " - " + this.getPozicio();
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

    public void pozicioRandomizalas() {
        this.pozicio.randomizalas();
        this.hajo.getPozicio().setPozicio(pozicio.getSorPozicio(), pozicio.getOszlopPozicio());
    }
}
