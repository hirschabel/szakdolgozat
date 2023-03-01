package hu.szakdolgozat;

import lombok.Getter;

@Getter
public class Hajo {
    private final Pozicio pozicio; // bal felső sarok pozíciója
    private int szint;
    // 0 - 3x3 hajó
    // 1 - víz visszatöltés
    // 2 - étel visszatöltés
    // 3 - víz + étel növelés
    // 4 - víz + étel növelés
    // 5 - 1 háló (1)
    // 6 - víz + étel növelés
    // 7 - 2 háló (3)
    // 8 - víz + étel növelés
    // 9 - víz + étel növelés
    // 10 - víz + étel növelés [MAX]

    public Hajo(Pozicio pozicio, int szint) {
        this.pozicio = pozicio;
        this.szint = szint;
    }

    public void szintlepes() {
        this.szint++;
        // TODO: építmények hozzáadása
        //   - Étel / Ital / Elfogó háló (/ élet regeneráló?)
    }

    private void vizLetrehozas() {
        // TODO
    }

    private void etelLetrehozas() {
        // TODO
    }

    private void vizEtelNoveles() {
        // TODO
    }

    private void haloHozzaadas() {
        // TODO
    }
}
