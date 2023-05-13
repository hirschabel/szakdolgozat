package hu.szakdolgozat.adatok;

import hu.szakdolgozat.jatekos.Jatekos;
import lombok.Getter;
import lombok.Setter;

import java.net.Socket;

@Getter
@Setter
public class Csatlakozas {
    private Jatekos jatekos;
    private Socket kliens;
    private String utasitas;

    public Csatlakozas(Socket kliens) {
        this.kliens = kliens;
        this.utasitas = "null";
        this.jatekos = null;
    }
}
