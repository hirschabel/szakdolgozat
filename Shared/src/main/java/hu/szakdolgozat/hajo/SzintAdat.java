package hu.szakdolgozat.hajo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class SzintAdat {
    @Id
    private int id;
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
