package hu.szakdolgozat;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
public class Eroforras implements Serializable {
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
        this.max_elet = max_elet;
        this.max_ital = max_ital;
        this.max_etel = max_etel;
        eroforrasBeallitas();
    }

    public void eletNoveles(int max_elet) {
        this.max_elet = max_elet;
        eroforrasBeallitas();
    }

    public void italNoveles(int max_ital) {
        this.max_ital = max_ital;
        eroforrasBeallitas();
    }

    public void etelNoveles(int max_etel) {
        this.max_etel = max_etel;
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

    public boolean isHalott() {
        return elet <= 0 || ital <= 0 || etel <= 0;
    }

    private void eroforrasBeallitas() {
        elet = max_elet;
        ital = max_ital;
        etel = max_etel;
    }

    public void italVisszatoltes() {
        if (ital < 100) {
            this.ital++;
        }
    }

    public void etelVisszatoltes() {
        if (etel < 100) {
            this.etel++;
        }
    }
}
