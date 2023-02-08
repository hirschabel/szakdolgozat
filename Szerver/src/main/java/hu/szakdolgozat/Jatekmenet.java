package hu.szakdolgozat;

import java.util.ArrayList;
import java.util.List;

public class Jatekmenet implements Runnable {
    private final int[][] terkep;
    private final List<Csatlakozas> csatlakozasok;
    private final JatekosLista jatekosLista2;
    private final TerkepLista terkepLista;

    public Jatekmenet(int[][] terkep, List<Csatlakozas> csatlakozasok, JatekosLista jatekosLista2, TerkepLista terkepLista) {
        this.terkep = terkep;
        this.csatlakozasok = csatlakozasok;
        this.jatekosLista2 = jatekosLista2;
        this.terkepLista = terkepLista;
    }

    @Override
    public void run() {
        List<Jatekos> jatekosok = new ArrayList<>();
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

    private boolean mozoghatOda(int sor, int oszlop) { // TODO akadály kereséssel
        return sor >= 0 && oszlop >= 0 && sor < 100 && oszlop < 100;
    }
}
