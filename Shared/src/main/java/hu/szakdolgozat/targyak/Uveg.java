package hu.szakdolgozat.targyak;

import hu.szakdolgozat.Pozicio;
import hu.szakdolgozat.TerkepKod;

public class Uveg extends Targy {
    public static final int ID = TerkepKod.UVEG;

    public Uveg(Pozicio pozicio) {
        super(ID, pozicio);
    }
}
