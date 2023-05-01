package hu.szakdolgozat;

import hu.szakdolgozat.adatok.Csatlakozas;
import hu.szakdolgozat.adatok.JatekAdatLista;
import hu.szakdolgozat.kommunikacio.KliensKapcsolat;
import hu.szakdolgozat.logika.Jatekmenet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Szerver {
    public static final int SZERVER_PORT = 52564;
    private static final int TICK_MILLISECOND = 300;
    private static final int MAX_JATEKOS_SZAM = 10;
    private int jatekosSzam;
    private ServerSocket szerver;
    private List<Csatlakozas> csatlakozasok;
    private JatekAdatLista jatekAdatLista;
    private ScheduledExecutorService executor;

    public void startSzerver() {
        System.out.println("---Szerver---");
        try (ServerSocket serverSocket = new ServerSocket(SZERVER_PORT)) {
            szerver = serverSocket;
            long[][] terkep = new long[100][100];
            csatlakozasok = new ArrayList<>();
            jatekAdatLista = new JatekAdatLista();
            Runnable jatekmenet = new Jatekmenet(terkep, csatlakozasok, jatekAdatLista);

            executor = Executors.newScheduledThreadPool(1);
            executor.scheduleAtFixedRate(jatekmenet, 0, TICK_MILLISECOND, TimeUnit.MILLISECONDS);

            csatlakozasFogadas();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void csatlakozasFogadas() {
        while (jatekosSzam < MAX_JATEKOS_SZAM) {
            try {
                Socket kliens = szerver.accept();
                Csatlakozas csatlakozas = new Csatlakozas(kliens);
                csatlakozasok.add(csatlakozas);
                jatekosSzam++;
                new Thread(new KliensKapcsolat(this, csatlakozas, jatekAdatLista)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteCsatlakozas(Csatlakozas csatlakozas) {
        jatekosSzam--;
        csatlakozasok.remove(csatlakozas);
        csatlakozasFogadas();
    }

    public static void main(String[] args) {
        Szerver szerver = new Szerver();
        szerver.startSzerver();
    }
}
