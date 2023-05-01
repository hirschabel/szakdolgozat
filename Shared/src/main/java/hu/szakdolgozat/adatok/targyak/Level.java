package hu.szakdolgozat.adatok.targyak;

import hu.szakdolgozat.Pozicio;
import hu.szakdolgozat.TerkepKodok;

public class Level extends Targy {
    public static final long ID = TerkepKodok.LEVEL;

    public Level(Pozicio pozicio) {
        super(ID, pozicio);
    }
}
