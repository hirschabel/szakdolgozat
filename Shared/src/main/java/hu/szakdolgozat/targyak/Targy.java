package hu.szakdolgozat.targyak;

import hu.szakdolgozat.Pozicio;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Targy {
    private Pozicio pozicio;
    private int id;

    public Targy(int id, Pozicio pozicio) {
        this.id = id;
        this.pozicio = pozicio;
    }
}
