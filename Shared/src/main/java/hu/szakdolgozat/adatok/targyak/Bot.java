package hu.szakdolgozat.adatok.targyak;

import hu.szakdolgozat.Pozicio;
import hu.szakdolgozat.TerkepKodok;

public class Bot extends Targy{
    public static final long ID = TerkepKodok.BOT;

    public Bot(Pozicio pozicio) {
        super(ID, pozicio);
    }
}
