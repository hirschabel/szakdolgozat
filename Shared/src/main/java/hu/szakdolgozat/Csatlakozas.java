package hu.szakdolgozat;

import lombok.Data;

import java.net.Socket;

@Data
public class Csatlakozas {
    private Jatekos jatekos;
    private Socket kliens;
    public Csatlakozas(Jatekos jatekos, Socket kliens) {
        this.jatekos = jatekos;
        this.kliens = kliens;
    }
}
