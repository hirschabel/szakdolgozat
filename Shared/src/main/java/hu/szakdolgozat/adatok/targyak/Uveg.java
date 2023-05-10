package hu.szakdolgozat.adatok.targyak;

import hu.szakdolgozat.Pozicio;
import hu.szakdolgozat.util.TerkepKodokUtil;

public class Uveg extends Targy {
    public static final long ID = TerkepKodokUtil.UVEG;

    public Uveg(Pozicio pozicio) {
        super(ID, pozicio);
    }
}
