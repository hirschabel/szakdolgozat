package hu.szakdolgozat;

import hu.szakdolgozat.targyak.Targy;
import lombok.Getter;
import hu.szakdolgozat.targyak.*;

@Getter
public class Eszkoztar {
    private int botSzam;
    private int levelSzam;
    private int uvegSzam;

    public Eszkoztar(int botSzam, int levelSzam, int uvegSzam) {
        this.botSzam = botSzam;
        this.levelSzam = levelSzam;
        this.uvegSzam = uvegSzam;
    }

    public void addTargy(int targyId) {
        switch (targyId) {
            case Bot.ID -> botSzam++;
            case Level.ID -> levelSzam++;
            case Uveg.ID -> uvegSzam++;
        }
    }

    public void setTargyak(int botSzam, int levelSzam, int uvegSzam) {
        this.botSzam = botSzam;
        this.levelSzam = levelSzam;
        this.uvegSzam = uvegSzam;
    }
}
