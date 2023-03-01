package hu.szakdolgozat.targyak;

import hu.szakdolgozat.Pozicio;

public class Bot extends Targy{
    public static final int ID = 0x00001000;

    public Bot(Pozicio pozicio) {
        super(ID, pozicio);
    }
}
