package hu.szakdolgozat.targyak;

import hu.szakdolgozat.Pozicio;
import hu.szakdolgozat.TerkepKod;

public class Bot extends Targy{
    public static final long ID = TerkepKod.BOT;

    public Bot(Pozicio pozicio) {
        super(ID, pozicio);
    }
}
