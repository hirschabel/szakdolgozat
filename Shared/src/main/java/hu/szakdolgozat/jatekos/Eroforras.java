package hu.szakdolgozat.jatekos;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
public class Eroforras implements Serializable {
    private static final int ELET_VISSZATOLTES = 5;
    private int max_elet;
    private int max_ital;
    private int max_etel;

    @Setter
    private int elet;

    @Setter
    private int ital;

    @Setter
    private int etel;

    public Eroforras(int max_elet, int max_ital, int max_etel) {
        setMax(max_elet, max_ital, max_etel);
    }

    public void italNoveles(int max_ital) {
        this.max_ital += max_ital;
        eroforrasBeallitas();
    }

    public void etelNoveles(int max_etel) {
        this.max_etel += max_etel;
        eroforrasBeallitas();
    }

    public void eletCsokkentes(int osszeg) {
        elet = elet > osszeg ? (elet - osszeg) : 0;
    }

    public void italCsokkentes() {
        ital = ital > 1 ? (ital - 1) : 0;
    }

    public void etelCsokkentes() {
        etel = etel > 1 ? (etel - 1) : 0;
    }

    public boolean halott() {
        return elet <= 0 || ital <= 0 || etel <= 0;
    }

    private void eroforrasBeallitas() {
        elet = max_elet;
        ital = max_ital;
        etel = max_etel;
    }

    public void italVisszatoltes() {
        ital = Math.min((ital + 1), max_ital);
    }

    public void etelVisszatoltes() {
        etel = Math.min((etel + 1), max_etel);
    }

    public void eletVisszatoltes() {
        elet = Math.min((elet + ELET_VISSZATOLTES), max_elet);
    }

    public void setMax(int maxElet, int maxItal, int maxEtel) {
        this.max_elet = maxElet;
        this.max_ital = maxItal;
        this.max_etel = maxEtel;
        eroforrasBeallitas();
    }
}
