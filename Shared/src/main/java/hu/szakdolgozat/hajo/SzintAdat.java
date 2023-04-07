package hu.szakdolgozat.hajo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SzintAdat {
    private final int[] szuksegesTargyak;
    private final int italNoveles;
    private final int etelNoveles;
    private final boolean italVisszatoltes;
    private final boolean etelVisszatoltes;
    private final boolean[] targyGeneralas;

    public boolean tudVisszatolteni() {
        return italVisszatoltes || etelVisszatoltes;
    }
}
