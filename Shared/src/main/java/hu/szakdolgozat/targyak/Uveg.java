package hu.szakdolgozat.targyak;

import hu.szakdolgozat.Pozicio;

public class Uveg extends Targy {
    public static final int ID = 0x00100000;

    public Uveg(Pozicio pozicio) {
        super(ID, pozicio);
    }
}
