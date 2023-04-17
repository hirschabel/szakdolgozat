package hu.szakdolgozat;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Random;

@Embeddable
@Data
@NoArgsConstructor
public class Pozicio implements Serializable {
    private int sorPozicio;
    private int oszlopPozicio;

    public Pozicio(int sorPozicio, int oszlopPozicio) {
        setPozicio(sorPozicio, oszlopPozicio);
    }

    public void setPozicio(int sor, int oszlop) {
        this.sorPozicio = Math.max(sor, 0);
        this.oszlopPozicio = Math.max(oszlop, 0);
    }

    public void setPozicio(Pozicio pozicio) {
        this.sorPozicio = Math.max(pozicio.getSorPozicio(), 0);
        this.oszlopPozicio = Math.max(pozicio.getOszlopPozicio(), 0);
    }

    public void mozgasRelativ(int sorDiff, int oszlopDiff) {
        int sorPoz = sorPozicio + sorDiff;
        int oszlopPoz = oszlopPozicio + oszlopDiff;
        mozgasIde(sorPoz, oszlopPoz);
    }

    private void mozgasIde(int sor, int oszlop) {
        if (sor >= 0 && sor < 100 && oszlop >= 0 && oszlop < 100) {
            sorPozicio = sor;
            oszlopPozicio = oszlop;
        }
    }

    @Override
    public String toString() {
        return "(" + sorPozicio + "," + oszlopPozicio + ")";
    }

    public boolean isRajta(Pozicio poz) {
        return poz.getSorPozicio() == sorPozicio && poz.getOszlopPozicio() == oszlopPozicio;
    }

    public boolean isHajon(Pozicio hajoPoz) {
        return (hajoPoz.getSorPozicio() == sorPozicio && hajoPoz.getOszlopPozicio() == oszlopPozicio
                || hajoPoz.getSorPozicio() + 1 == sorPozicio && hajoPoz.getOszlopPozicio() == oszlopPozicio
                || hajoPoz.getSorPozicio() == sorPozicio && hajoPoz.getOszlopPozicio() + 1 == oszlopPozicio
                || hajoPoz.getSorPozicio() + 1 == sorPozicio && hajoPoz.getOszlopPozicio() + 1 == oszlopPozicio);
    }

    public Pozicio randomizalas() {
        Random r = new Random();
        this.sorPozicio = r.nextInt(100);
        this.oszlopPozicio = r.nextInt(100);
        return this;
    }
}
