package hu.szakdolgozat;

import lombok.Data;

@Data
public class Pozicio { //TODO szerializálhatóvá tenni, ha küldeni akarjuk writeObject-tel
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

    private boolean latja(int sor, int oszlop, int oldal) {
        return sor >= sorPozicio - oldal && sor < sorPozicio + oldal && oszlop >= oszlopPozicio - oldal && oszlop < oszlopPozicio + oldal;
    }

    public Pozicio getRelativePoz(Pozicio pozicio, int negyzetOldal) {
        int sor = pozicio.getSorPozicio();
        int oszlop = pozicio.getOszlopPozicio();
        int oldal = negyzetOldal / 2;
        if (!latja(sor, oszlop, oldal)) {
            return null;
        }
        return new Pozicio(oldal - (sorPozicio - sor), oldal - (oszlopPozicio - oszlop));
    }

    public boolean isRajta(Pozicio poz) {
        return poz.getSorPozicio() == sorPozicio && poz.getOszlopPozicio() == oszlopPozicio;
    }
}
