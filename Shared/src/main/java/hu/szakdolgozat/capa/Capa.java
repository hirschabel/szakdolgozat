package hu.szakdolgozat.capa;

import hu.szakdolgozat.Pozicio;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Capa {
    public static final int SEBZES = 30;
    private Pozicio pozicio;

    public Capa() {
        this.pozicio = new Pozicio(0,0).randomizalas();
    }
}
