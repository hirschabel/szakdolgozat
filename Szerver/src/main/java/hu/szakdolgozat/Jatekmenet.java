package hu.szakdolgozat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Jatekmenet implements Runnable {
    private final int[][] terkep;
    private final List<Csatlakozas> csatlakozasok;
    private final JatekAdatLista jatekAdatLista;

    public Jatekmenet(int[][] terkep, List<Csatlakozas> csatlakozasok, JatekAdatLista jatekAdatLista) {
        this.csatlakozasok = csatlakozasok;
        this.terkep = terkep;
        this.jatekAdatLista = jatekAdatLista;
        for (int[] sor : terkep) {
            Arrays.fill(sor, 1);
        }
    }

    @Override
    public void run() {
        List<Jatekos> jatekosok = new ArrayList<>();
        // TODO items
        for (Csatlakozas csatlakozas : csatlakozasok) {
            Jatekos currJatekos = csatlakozas.getJatekos();
            if (currJatekos != null) {
                jatekosok.add(currJatekos);
                inputKezeles(csatlakozas.getUtasitas(), currJatekos.getPozicio());
            }
            csatlakozas.setUtasitas("null");
        }

        jatekAdatLista.send(new JatekAdat(jatekosok, terkep));
    }

    private void inputKezeles(String irany, Pozicio pozicio) {
        switch (irany) {
            case "W" -> {
                pozicio.mozgasRelativ(-1, 0);
            }
            case "D" -> {
                pozicio.mozgasRelativ(0, 1);
            }
            case "A" -> {
                pozicio.mozgasRelativ(0, -1);
            }
            case "S" -> {
                pozicio.mozgasRelativ(1, 0);
            }
            default -> {
                pozicio.mozgasRelativ(0, 0);
            }
        }
    }
}
