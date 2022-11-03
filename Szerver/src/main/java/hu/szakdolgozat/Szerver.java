package hu.szakdolgozat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Szerver {
    private final int SZERVER_PORT = 52564;
    private ServerSocket szerver;
    private int jatekosSzam;

    public Szerver() {
        System.out.println("---Szerver---");
        jatekosSzam = 0;
        try {
            szerver = new ServerSocket(SZERVER_PORT);
            /*
            TODO
             * (Egységes térkép létrehozása / betöltése)
                 -> Inkább minden szerver indításkor létrehozása, hogy ne legyen játékos egybeesés (ugyanott léptek ki, majd egyszerre lépnek vissza)
             * Játékos elhelyezése a pályán
            */
            csatlakozasFogadas();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void jatekosSzamCsokkent() {
        jatekosSzam--;
    }

    private void csatlakozasFogadas() throws IOException { // TODO: játékosszám maximum elérése után, ha lecsatlakozik valaki, akkor is fogadjon
        while (jatekosSzam < 10) {
            Socket kliens = szerver.accept();
            jatekosSzam++;
            Thread t = new Thread(new KliensKapcsolat(kliens, this::jatekosSzamCsokkent));
            t.start();
        }
    }

    public static void main(String[] args) {
        new Szerver();
    }
}
