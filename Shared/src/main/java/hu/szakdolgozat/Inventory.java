package hu.szakdolgozat;

import hu.szakdolgozat.targyak.Targy;
import lombok.Getter;

@Getter
public class Inventory {
    private int botSzam;
    private int levelSzam;
    private int uvegSzam;

    public Inventory(int botSzam, int levelSzam, int uvegSzam) {
        this.botSzam = botSzam;
        this.levelSzam = levelSzam;
        this.uvegSzam = uvegSzam;
    }

    public void addTargy(Targy targy) {
        switch (targy.getId()) {
            case 4 -> botSzam++;
            case 5 -> levelSzam++;
            case 6 -> uvegSzam++;
        }
    }

    public void addTargy(int targyId) {
        switch (targyId) {
            case 4 -> botSzam++;
            case 5 -> levelSzam++;
            case 6 -> uvegSzam++;
        }
    }

    public void setTargyak(int botSzam, int levelSzam, int uvegSzam) {
        this.botSzam = botSzam;
        this.levelSzam = levelSzam;
        this.uvegSzam = uvegSzam;
    }
}
