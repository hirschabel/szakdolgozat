package hu.szakdolgozat.adatok.targyak;

import hu.szakdolgozat.Pozicio;
import hu.szakdolgozat.util.TerkepKodokUtil;

public class Level extends Targy {
    public static final long ID = TerkepKodokUtil.LEVEL;

    public Level(Pozicio pozicio) {
        super(ID, pozicio);
    }
}
