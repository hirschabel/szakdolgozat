package hu.szakdolgozat.adatok;

import hu.szakdolgozat.jatekos.Eroforras;
import hu.szakdolgozat.jatekos.Eszkoztar;
import hu.szakdolgozat.Pozicio;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class Adat implements Serializable {
    private final long[][] terkep;
    private final Pozicio pozicio;
    private final Eszkoztar eszkoztar;
    private final Eroforras eroforras;
    private final int szint;
    private final int[] targySzintlepeshez;

    public Adat() {
        terkep = null;
        pozicio = new Pozicio(0, 0);
        eszkoztar = new Eszkoztar(0, 0,0);
        eroforras = new Eroforras(0, 0, 0);
        szint = 0;
        targySzintlepeshez = null;
    }
}
