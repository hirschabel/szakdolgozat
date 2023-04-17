package hu.szakdolgozat.targyak;

import hu.szakdolgozat.Pozicio;
import hu.szakdolgozat.TerkepKod;

public class Level extends Targy {
    public static final long ID = TerkepKod.LEVEL;

    public Level(Pozicio pozicio) {
        super(ID, pozicio);
    }
}
