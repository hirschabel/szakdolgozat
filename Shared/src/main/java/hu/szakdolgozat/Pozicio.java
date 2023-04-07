package hu.szakdolgozat;

import lombok.Data;

import java.io.Serializable;

@Data
public class Pozicio implements Serializable {
    private int sorPozicio;
    private int oszlopPozicio;

    public Pozicio(int sorPozicio, int oszlopPozicio) {
        this.sorPozicio = sorPozicio;
        this.oszlopPozicio = oszlopPozicio;
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
                || hajoPoz.getSorPozicio() - 1 == sorPozicio && hajoPoz.getOszlopPozicio() == oszlopPozicio
                || hajoPoz.getSorPozicio() == sorPozicio && hajoPoz.getOszlopPozicio() + 1 == oszlopPozicio
                || hajoPoz.getSorPozicio() - 1 == sorPozicio && hajoPoz.getOszlopPozicio() + 1 == oszlopPozicio);
    }
}
