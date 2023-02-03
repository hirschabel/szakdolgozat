package hu.szakdolgozat;

import java.util.ArrayList;
import java.util.List;

public class Jatekmenet implements Runnable {
    private int[][] terkep;
    private List<Csatlakozas> csatlakozasok;
    private JatekosLista jatekosLista2;
    private TerkepLista terkepLista;
    private int[][] terkep3;

    private List<Jatekos> jatekosok;

    public Jatekmenet(int[][] terkep, List<Csatlakozas> csatlakozasok, JatekosLista jatekosLista2, TerkepLista terkepLista) {
        this.terkep = terkep;
        this.csatlakozasok = csatlakozasok;
        this.jatekosLista2 = jatekosLista2;
        this.terkepLista = terkepLista;

        terkep3 = new int[][] {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
    }

    @Override
    public void run() {
        jatekosok = new ArrayList<>();
        for (Csatlakozas csatlakozas : csatlakozasok) {
            Jatekos currJatekos = csatlakozas.getJatekos();
            if (currJatekos != null) {
                jatekosok.add(currJatekos);
                currJatekos.setPozicio(inputKezeles(csatlakozas.getUtasitas(), currJatekos.getPozicio()));
            }
            csatlakozas.setUtasitas("null");
        }

        jatekosLista2.send(jatekosok);
        terkepLista.send(terkep);
    }

    private Pozicio inputKezeles(String irany, Pozicio pozicio) {
        switch (irany) {
            case "W" -> {
                return mozgas(-1, 0, pozicio);
            }
            case "D" -> {
                return mozgas(0, 1, pozicio);
            }
            case "A" -> {
                return mozgas(0, -1, pozicio);
            }
            case "S" -> {
                return mozgas(1, 0, pozicio);
            }
            default -> {
                return mozgas(0, 0, pozicio);
            }
        }
    }

    private Pozicio mozgas(int sorDiff, int oszlopDiff, Pozicio pozicio) {
        int sor = pozicio.getSorPozicio();
        int oszlop = pozicio.getOszlopPozicio();
        if (mozoghatOda(sor + sorDiff, oszlop + oszlopDiff)) {
            terkep[sor][oszlop] = 0;
            sor = sor + sorDiff;
            oszlop = oszlop + oszlopDiff;
            terkep[sor][oszlop] = 1;

            return new Pozicio(sor, oszlop);
        }
        return pozicio;
    }

    private boolean mozoghatOda(int x, int y) { // TODO valid határértékekkel + akadály kereséssel
        return x >= 0 && y >= 0 && x < 100 && y < 100;
    }
}
