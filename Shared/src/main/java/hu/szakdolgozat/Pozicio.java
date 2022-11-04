package hu.szakdolgozat;

import lombok.Data;

@Data
public class Pozicio {
    private int sorPozicio;
    private int oszlopPozicio;

    public Pozicio(int sorPozicio, int oszlopPozicio) {
        this.sorPozicio = sorPozicio;
        this.oszlopPozicio = oszlopPozicio;
    }
}
