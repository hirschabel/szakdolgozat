package hu.szakdolgozat.adatok.targyak;

import hu.szakdolgozat.Pozicio;
import hu.szakdolgozat.TerkepKodok;

public class Uveg extends Targy {
    public static final long ID = TerkepKodok.UVEG;

    public Uveg(Pozicio pozicio) {
        super(ID, pozicio);
    }
}
