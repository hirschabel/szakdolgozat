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

    @Override
    public String toString() {
        return "(" + sorPozicio + "," + oszlopPozicio + ")";
    }
}
