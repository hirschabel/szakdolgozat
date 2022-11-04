package hu.szakdolgozat;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Szerver {
    private final int SZERVER_PORT = 52564;
    private ServerSocket szerver;
    private List<Csatlakozas> csatlakozasok;
    private int[][] terkep;

    public Szerver() {
        System.out.println("---Szerver---");
        try {
            szerver = new ServerSocket(SZERVER_PORT);
            terkep = new int[100][100];
            csatlakozasok = new ArrayList<>();

            Jatekmenet jatekmenet = new Jatekmenet(terkep, csatlakozasok);

            csatlakozasFogadas();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void csatlakozasFogadas() throws IOException { // TODO: játékosszám maximum elérése után, ha lecsatlakozik valaki, akkor is fogadjon
        while (true) {
            Socket kliens = szerver.accept();
            Csatlakozas csatlakozas = new Csatlakozas(null, kliens);
            csatlakozasok.add(csatlakozas);

            new Thread(new TesztKliensKapcsolat(csatlakozas, deleteCsatlakozas)).start();
        }
    }

    Function <Csatlakozas, Boolean> deleteCsatlakozas = e -> csatlakozasok.remove(e);

    public static void main(String[] args) {
        new Szerver();
    }
}


