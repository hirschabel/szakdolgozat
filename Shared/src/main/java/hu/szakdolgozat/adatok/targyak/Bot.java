package hu.szakdolgozat.adatok.targyak;

import hu.szakdolgozat.Pozicio;
import hu.szakdolgozat.util.TerkepKodokUtil;

public class Bot extends Targy{
    public static final long ID = TerkepKodokUtil.BOT;

    public Bot(Pozicio pozicio) {
        super(ID, pozicio);
    }
}
