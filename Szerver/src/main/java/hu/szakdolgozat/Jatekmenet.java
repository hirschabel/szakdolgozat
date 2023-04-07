package hu.szakdolgozat;

import hu.szakdolgozat.targyak.Bot;
import hu.szakdolgozat.targyak.Level;
import hu.szakdolgozat.targyak.Targy;
import hu.szakdolgozat.targyak.Uveg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Jatekmenet implements Runnable {
    private static int lepes = 0;
    private final int[][] terkep;
    private final List<Csatlakozas> csatlakozasok;
    private final List<Targy> targyak;
    private final JatekAdatLista jatekAdatLista;

    public Jatekmenet(int[][] terkep, List<Csatlakozas> csatlakozasok, JatekAdatLista jatekAdatLista) {
        this.csatlakozasok = csatlakozasok;
        this.terkep = terkep;
        this.jatekAdatLista = jatekAdatLista;
        this.targyak = new ArrayList<>();
    }

    @Override
    public void run() {
        for (int[] sor : terkep) {
            Arrays.fill(sor, 0x00000001); //
        }
        if (lepes % 3 == 0) {
            // 1. Tárgy léptetés (törlés, ami kiesik)
            targyLeptetes();

            // 2. Item generálás
            tagyGeneralas();
        }

        // 3. Játékos input kezelés + erőforrás
        List<Jatekos> jatekosok = new ArrayList<>();
        jatekosInput(jatekosok);

        // 4. Tárgy felvétel
        targyFelvetel(jatekosok);

        // 5. Térkép frissítés
        terkepFrissites();

        // 6. Adat küldés
        lepes++;
        jatekAdatLista.send(new JatekAdat(jatekosok, terkep));
    }

    private void targyLeptetes() {
        for (Targy targy : targyak) {
            int sorPoz = targy.getPozicio().getSorPozicio() + 1;
            if (sorPoz < 100) {
                targy.getPozicio().setSorPozicio(sorPoz);
            }
        }
    }

    public void tagyGeneralas() {
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            int id = random.nextInt(3);
            int sor = random.nextInt(100);
            Targy targy = switch (id) {
                case 0 -> new Uveg(new Pozicio(0, sor));
                case 1 -> new Bot(new Pozicio(0, sor));
                case 2 -> new Level(new Pozicio(0, sor));
                default -> null;
            };

            targyak.add(targy);
        }
    }

    private void jatekosInput(List<Jatekos> jatekosok) {
        for (Csatlakozas csatlakozas : csatlakozasok) {
            Jatekos currJatekos = csatlakozas.getJatekos();
            if (currJatekos != null) {
                jatekosok.add(currJatekos);
                if (lepes % 3 == 0) { // lehetne double?
                    currJatekos.getEroforrasok().italCsokkentes();
                } else if (lepes % 5 == 0) {
                    currJatekos.getEroforrasok().etelCsokkentes();
                }
                currJatekos.getEroforrasok().isHalott(); // TODO: halál

                inputKezeles(csatlakozas.getUtasitas(), currJatekos);

                currJatekos.getHajo().szintlepes(currJatekos.getEszkoztar());
                if (currJatekos.tudVisszatolteni() && currJatekos.getPozicio().isHajon(currJatekos.getHajo().getPozicio())) {
                    currJatekos.visszatolt(); // ÉTEL / ITAL VISSZATÖLTÉS
                }
            }
            csatlakozas.setUtasitas("null");
        }
    }

    private void targyFelvetel(List<Jatekos> jatekosok) {
        targyak.removeIf(p -> {
            for (Jatekos jatekos : jatekosok) {
                if (!jatekos.getPozicio().isHajon(jatekos.getHajo().getPozicio()) && jatekos.getPozicio().isRajta(p.getPozicio())) {
                    jatekos.getEszkoztar().addTargy(p.getId());
                    return true;
                }
            }
            return false;
        });
    }

    private void terkepFrissites() {
        for (Targy targy : targyak) {
            terkep[targy.getPozicio().getSorPozicio()][targy.getPozicio().getOszlopPozicio()] |= targy.getId();
        }
    }

    /***
     * Lehetséges inputok
     *  - W: Mozgás egy mezővel fentebb
     *  - D: Mozgás egy mezővel jobbra
     *  - A: Mozgás egy mezővel balra
     *  - S: Mozgás egy mezővel lentebb
     *  - I: Hajó mozgatása egy mezővel fentebb
     *  - L: Hajó mozgatása egy mezővel jobbra
     *  - J: Hajó mozgatása egy mezővel balra
     *  - K: Hajó mozgatása egy mezővel lentebb
     * @param irany A játékostól kapott mozgás input
     * @param jatekos A mozgó játékos
     */
    private void inputKezeles(String irany, Jatekos jatekos) {
        Pozicio jatekosPoz = jatekos.getPozicio();
        Pozicio hajoPoz = jatekos.getHajo().getPozicio();
        int sorDiff = 0;
        int oszlDiff = 0;
        int hajoSorDiff = 0;
        int hajoOszlDiff = 0;
        switch (irany) {
            case "W" -> sorDiff = -1;
            case "D" -> oszlDiff = 1;
            case "S" -> sorDiff = 1;
            case "A" -> oszlDiff = -1;
            case "I" -> hajoSorDiff = -1;
            case "L" -> hajoOszlDiff = 1;
            case "K" -> hajoSorDiff = 1;
            case "J" -> hajoOszlDiff = -1;
        }
        jatekosPoz.mozgasRelativ(sorDiff, oszlDiff);
        hajoPoz.mozgasRelativ(hajoSorDiff, hajoOszlDiff);
    }
}
