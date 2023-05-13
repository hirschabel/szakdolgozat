package hu.szakdolgozat;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Random;

@Embeddable
@Data
@NoArgsConstructor
public class Pozicio implements Serializable {
    private static final Random random = new SecureRandom();
    private int sorPozicio;
    private int oszlopPozicio;

    public Pozicio(int sorPozicio, int oszlopPozicio) {
        setPozicio(sorPozicio, oszlopPozicio);
    }

    public void setPozicio(int sor, int oszlop) {
        this.sorPozicio = Math.max(0, Math.min(sor, 99));
        this.oszlopPozicio = Math.max(0, Math.min(oszlop, 99));
    }

    public void setPozicio(Pozicio pozicio) {
        setPozicio(pozicio.getSorPozicio(), pozicio.getOszlopPozicio());
    }

    public void mozgasRelativ(int sorDiff, int oszlopDiff) {
        int sorPoz = sorPozicio + sorDiff;
        int oszlopPoz = oszlopPozicio + oszlopDiff;
        setPozicio(sorPoz, oszlopPoz);
    }

    public boolean isRajta(Pozicio poz) {
        return poz.getSorPozicio() == sorPozicio && poz.getOszlopPozicio() == oszlopPozicio;
    }

    public boolean isHajon(Pozicio hajoPoz) {
        int hajoSor = hajoPoz.getSorPozicio();
        int hajoOszlop = hajoPoz.getOszlopPozicio();

        return (hajoSor == sorPozicio && hajoOszlop == oszlopPozicio
                || hajoSor + 1 == sorPozicio && hajoOszlop == oszlopPozicio
                || hajoSor == sorPozicio && hajoOszlop + 1 == oszlopPozicio
                || hajoSor + 1 == sorPozicio && hajoOszlop + 1 == oszlopPozicio);
    }

    public Pozicio randomizalas() {
        this.sorPozicio = random.nextInt(100);
        this.oszlopPozicio = random.nextInt(100);
        return this;
    }

    @Override
    public String toString() {
        return "(" + sorPozicio + "," + oszlopPozicio + ")";
    }
}
