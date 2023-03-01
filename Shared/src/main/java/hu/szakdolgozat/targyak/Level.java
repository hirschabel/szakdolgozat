package hu.szakdolgozat.targyak;

import hu.szakdolgozat.Pozicio;

public class Level extends Targy {
    public static final int ID = 0x00010000;

    public Level(Pozicio pozicio) {
        super(ID, pozicio);
    }
}
