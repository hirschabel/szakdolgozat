package hu.szakdolgozat.jatekos;

import hu.szakdolgozat.adatok.targyak.Bot;
import hu.szakdolgozat.adatok.targyak.Level;
import hu.szakdolgozat.adatok.targyak.Uveg;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
public class Eszkoztar implements Serializable {
    private int botSzam;
    private int levelSzam;
    private int uvegSzam;

    public Eszkoztar(int botSzam, int levelSzam, int uvegSzam) {
        setTargyak(botSzam, levelSzam, uvegSzam);
    }

    public void addTargy(long targyId) {
        if (Bot.ID == targyId) {
            botSzam++;
        }
        if (Level.ID == targyId) {
            levelSzam++;
        }
        if (Uveg.ID == targyId) {
            uvegSzam++;
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

    public void urites() {
        this.botSzam = 0;
        this.levelSzam = 0;
        this.uvegSzam = 0;
    }
}
