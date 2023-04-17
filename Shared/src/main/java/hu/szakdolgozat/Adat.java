package hu.szakdolgozat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
}
