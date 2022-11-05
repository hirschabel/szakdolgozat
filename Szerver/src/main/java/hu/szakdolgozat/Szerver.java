package hu.szakdolgozat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class Szerver {
    private final int SZERVER_PORT = 52564;
    private ServerSocket szerver;
    private List<Csatlakozas> csatlakozasok;
    private int[][] terkep;
    private Terkep terkep99;

    public Szerver() {
        System.out.println("---Szerver---");
        try {
            szerver = new ServerSocket(SZERVER_PORT);
            terkep = new int[100][100];
            csatlakozasok = new ArrayList<>();

            //Jatekmenet jatekmenet = new Jatekmenet(terkep, csatlakozasok);
            terkep99 = new Terkep();
            Runnable jatekmenet = new Jatekmenet(terkep, csatlakozasok, terkep99);

            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executor.scheduleAtFixedRate(jatekmenet, 0, 250, TimeUnit.MILLISECONDS);


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

            new Thread(new TesztKliensKapcsolat(csatlakozas, deleteCsatlakozas, terkep99)).start();
        }
    }

    Function <Csatlakozas, Boolean> deleteCsatlakozas = e -> csatlakozasok.remove(e);

    public static void main(String[] args) {
        new Szerver();
    }
}


