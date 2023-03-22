package hu.szakdolgozat;

import lombok.Data;

import java.net.Socket;

@Data
public class Csatlakozas {
    private Jatekos jatekos;
    private Socket kliens;
    private String utasitas;

    public Csatlakozas(Socket kliens) {
        this.kliens = kliens;
        this.utasitas = "null";
    }
}
