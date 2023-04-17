package hu.szakdolgozat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class JatekAdat {
    private List<Jatekos> jatekosok;
    private long[][] terkep;
}
