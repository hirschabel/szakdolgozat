package hu.szakdolgozat;

import lombok.Getter;
import hu.szakdolgozat.targyak.*;

import java.io.Serializable;

@Getter
public class Eszkoztar implements Serializable {
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

    public boolean targyCsokkentes(int[] csokkentes) {
        if (this.levelSzam < csokkentes[0] || this.botSzam < csokkentes[1] || this.uvegSzam < csokkentes[2]) {
            return false;
        }
        this.levelSzam -= csokkentes[0];
        this.botSzam -= csokkentes[1];
        this.uvegSzam -= csokkentes[2];
        return true;
    }
}
