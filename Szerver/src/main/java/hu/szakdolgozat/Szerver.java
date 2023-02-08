package hu.szakdolgozat;

import java.io.IOException;
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
    private final int TICK_MILLISECOND = 250;
    private ServerSocket szerver;
    private int[][] terkep;
    private List<Csatlakozas> csatlakozasok;
    private JatekosLista jatekosLista;
    private TerkepLista terkepLista;

    public Szerver() {
        System.out.println("---Szerver---");
        try {
            szerver = new ServerSocket(SZERVER_PORT);
            terkep = new int[100][100];
            csatlakozasok = new ArrayList<>();

            jatekosLista = new JatekosLista();
            terkepLista = new TerkepLista();
            Runnable jatekmenet = new Jatekmenet(terkep, csatlakozasok, jatekosLista, terkepLista);

            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executor.scheduleAtFixedRate(jatekmenet, 0, TICK_MILLISECOND, TimeUnit.MILLISECONDS);

            csatlakozasFogadas();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void csatlakozasFogadas() throws IOException { // TODO: játékosszám maximum elérése után, ha lecsatlakozik valaki, akkor is fogadjon
        while (true) {
            Socket kliens = szerver.accept();
            Csatlakozas csatlakozas = new Csatlakozas(kliens);
            csatlakozasok.add(csatlakozas);

            new Thread(new KliensKapcsolat(csatlakozas, deleteCsatlakozas, jatekosLista, terkepLista)).start();
        }
    }

    Function <Csatlakozas, Boolean> deleteCsatlakozas = e -> csatlakozasok.remove(e);

    public static void main(String[] args) {
        new Szerver();
    }
}


