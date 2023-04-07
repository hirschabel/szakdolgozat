package hu.szakdolgozat.hajo;

import hu.szakdolgozat.Eszkoztar;
import hu.szakdolgozat.Pozicio;
import lombok.Getter;

import java.util.Map;

@Getter
public class Hajo {
    // 0 - 3x3 hajó
    // 1 - víz visszatöltés
    // 2 - étel visszatöltés
    // 3 - 1 háló (1) ---> Tárgy generálás (levél)
    // 4 - víz + étel növelés
    // 5 - 1 háló (1) ---> Tárgy generálás (bot)
    // 6 - víz + étel növelés
    // 7 - 2 háló (3) ---> Tárgy generálás (Uveg)
    // 8 - víz + étel növelés
    // 9 - víz + étel növelés
    // 10 - víz + étel növelés [MAX]
    private static final Map<Integer, SzintAdat> SZINT_ADATOK = Map.ofEntries(
            Map.entry(0, new SzintAdat(new int[] {1, 1, 1}, 0, 0, true, false, new boolean[] {false, false, false})),
            Map.entry(1, new SzintAdat(new int[] {2, 2, 2}, 0, 0, true, true, new boolean[] {false, false, false})),
            Map.entry(2, new SzintAdat(new int[] {5, 5, 5}, 0, 0, true, true, new boolean[] {true, false, false})),
            Map.entry(3, new SzintAdat(new int[] {6, 6, 6}, 10, 10, true, true, new boolean[] {true, false, false})),
            Map.entry(4, new SzintAdat(new int[] {10, 10, 10}, 10, 10, true, true, new boolean[] {true, true, false})),
            Map.entry(5, new SzintAdat(new int[] {15, 10, 10}, 20, 20, true, true, new boolean[] {true, true, false})),
            Map.entry(6, new SzintAdat(new int[] {10, 15, 10}, 20, 20, true, true, new boolean[] {true, true, true})),
            Map.entry(7, new SzintAdat(new int[] {10, 10, 15}, 30, 30, true, true, new boolean[] {true, true, true})),
            Map.entry(8, new SzintAdat(new int[] {15, 15, 15}, 40, 40, true, true, new boolean[] {true, true, true})),
            Map.entry(9, new SzintAdat(new int[] {20, 20, 20}, 50, 50, true, true, new boolean[] {true, true, true})),
            Map.entry(10, new SzintAdat(null, 50, 50, true, true, new boolean[] {true, true, true}))
    );
    private final Pozicio pozicio; // bal felső sarok pozíciója [3x3 hajó]
    private int szint;
    private SzintAdat szintAdat;

    public Hajo(Pozicio pozicio, int szint) {
        this.pozicio = pozicio;
        this.szint = szint;
        this.szintAdat = SZINT_ADATOK.getOrDefault(szint, null);
    }

    public void szintlepes(Eszkoztar eszkoztar) {
        int[] minTargyak = szintAdat.getSzuksegesTargyak();
        if (minTargyak == null || !eszkoztar.targyCsokkentes(minTargyak)) {
            return;
        }

        this.szint++;
        this.szintAdat = SZINT_ADATOK.getOrDefault(szint, null);
    }
}
