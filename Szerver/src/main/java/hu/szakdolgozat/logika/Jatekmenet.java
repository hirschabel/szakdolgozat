package hu.szakdolgozat.logika;

import hu.szakdolgozat.Pozicio;
import hu.szakdolgozat.util.TerkepKodokUtil;
import hu.szakdolgozat.adatok.Csatlakozas;
import hu.szakdolgozat.adatok.JatekAdat;
import hu.szakdolgozat.adatok.JatekAdatLista;
import hu.szakdolgozat.capa.Utkereses;
import hu.szakdolgozat.dao.JatekosDao;
import hu.szakdolgozat.adatok.targyak.Bot;
import hu.szakdolgozat.adatok.targyak.Level;
import hu.szakdolgozat.adatok.targyak.Targy;
import hu.szakdolgozat.adatok.targyak.Uveg;
import hu.szakdolgozat.jatekos.Jatekos;
import lombok.Getter;
import lombok.Setter;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static hu.szakdolgozat.capa.Capa.SEBZES;

@Getter
public class Jatekmenet implements Runnable {
    private static final int MENTES_GYAKORISAG_LEPESBEN = 20;
    private static int lepes = 0;
    private final long[][] terkep;
    private final List<Csatlakozas> csatlakozasok;
    private final List<Targy> targyak;
    private final JatekAdatLista jatekAdatLista;
    private static final Random random = new SecureRandom();

    @Setter
    private JatekosDao jatekosDao;

    public Jatekmenet(long[][] terkep, List<Csatlakozas> csatlakozasok, JatekAdatLista jatekAdatLista) {
        this.csatlakozasok = csatlakozasok;
        this.terkep = terkep;
        this.jatekAdatLista = jatekAdatLista;
        this.targyak = new ArrayList<>();
        this.jatekosDao = new JatekosDao();
    }

    @Override
    public void run() {
        for (long[] sor : terkep) {
            Arrays.fill(sor, TerkepKodokUtil.TERKEP_MEZO);
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

        // Mentés
        if (lepes % MENTES_GYAKORISAG_LEPESBEN == 0) {
            new Thread(() -> new JatekosDao().jatekosokMentese(jatekosok)).start();
        }

        // 5. Térkép frissítés
        terkepFrissites(jatekosok);

        // Cápa mozgás
        for (Jatekos currJatekos : jatekosok) {
            Pozicio capaPoz = currJatekos.getCapa().getPozicio();
            Pozicio jatekosPoz = currJatekos.getPozicio();
            Pozicio hajoPoz = currJatekos.getHajo().getPozicio();
            int sor = jatekosPoz.getSorPozicio();
            int oszlop = jatekosPoz.getOszlopPozicio();

            if ((terkep[sor][oszlop] & TerkepKodokUtil.HAJO) == 0) {
                capaPoz.setPozicio(new Utkereses(terkep).utKereses(capaPoz, jatekosPoz));
            } else {
                capaPoz.setPozicio(new Utkereses(terkep).randomPoz(capaPoz, jatekosPoz));
            }
            terkep[capaPoz.getSorPozicio()][capaPoz.getOszlopPozicio()] |= TerkepKodokUtil.CAPA;

            if (capaPoz.isRajta(jatekosPoz)) {
                currJatekos.getEroforrasok().eletCsokkentes(SEBZES);
                if (currJatekos.halott()) {
                    terkep[sor][oszlop] &= (~TerkepKodokUtil.MASIK_JATEKOS);
                    terkep[capaPoz.getSorPozicio()][capaPoz.getOszlopPozicio()] &= (~TerkepKodokUtil.CAPA);
                    terkep[hajoPoz.getSorPozicio()][hajoPoz.getOszlopPozicio()] &= (~TerkepKodokUtil.HAJO);
                    terkep[hajoPoz.getSorPozicio() + 1][hajoPoz.getOszlopPozicio()] &= (~TerkepKodokUtil.HAJO);
                    terkep[hajoPoz.getSorPozicio()][hajoPoz.getOszlopPozicio() + 1] &= (~TerkepKodokUtil.HAJO);
                    terkep[hajoPoz.getSorPozicio() + 1][hajoPoz.getOszlopPozicio() + 1] &= (~TerkepKodokUtil.HAJO);
                    new Thread(() -> new JatekosDao().jatekosMentes(currJatekos)).start();
                }
            }
        }

        // 6. Adat küldés
        lepes++;
        jatekAdatLista.send(new JatekAdat(jatekosok, terkep));
    }

    public void targyLeptetes() {
        for (Targy targy : targyak) {
            int sorPoz = targy.getPozicio().getSorPozicio() + 1;
            if (sorPoz <= 100) {
                targy.getPozicio().setSorPozicio(sorPoz);
            }
        }
        targyak.removeIf(n -> n.getPozicio().getSorPozicio() == 100);
    }

    public void tagyGeneralas() {
        for (int i = 0; i < 5; i++) {
            int id = random.nextInt(3);
            int oszlop = random.nextInt(100);
            Targy targy = switch (id) {
                case 0 -> new Uveg(new Pozicio(0, oszlop));
                case 1 -> new Bot(new Pozicio(0, oszlop));
                case 2 -> new Level(new Pozicio(0, oszlop));
                default -> null;
            };

            targyak.add(targy);
        }
    }

    public void jatekosInput(List<Jatekos> jatekosok) {
        for (Csatlakozas csatlakozas : csatlakozasok) {
            Jatekos currJatekos = csatlakozas.getJatekos();
            if (currJatekos != null) {
                jatekosok.add(currJatekos);
                if (lepes % 3 == 0) {
                    currJatekos.getEroforrasok().italCsokkentes();
                    currJatekos.targyGeneralas();
                } else if (lepes % 5 == 0) {
                    currJatekos.getEroforrasok().etelCsokkentes();
                }
                inputKezeles(csatlakozas.getUtasitas(), currJatekos);

                if (currJatekos.halott()) {
                    new Thread(() -> new JatekosDao().jatekosMentes(currJatekos)).start();
                    continue;
                }

                currJatekos.getHajo().szintlepes(currJatekos.getEszkoztar());
                if (currJatekos.tudVisszatolteni() && currJatekos.getPozicio().isHajon(currJatekos.getHajo().getPozicio())) {
                    currJatekos.visszatolt(); // ÉTEL / ITAL VISSZATÖLTÉS
                }
                currJatekos.eletVisszatolt();
            }
            csatlakozas.setUtasitas("null");
        }
    }

    public void targyFelvetel(List<Jatekos> jatekosok) {
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

    public void terkepFrissites(List<Jatekos> jatekosok) {
        for (Targy targy : targyak) {
            terkep[targy.getPozicio().getSorPozicio()][targy.getPozicio().getOszlopPozicio()] |= targy.getId();
        }
        for (Jatekos jatekos : jatekosok) {
            Pozicio jatekosPoz = jatekos.getPozicio();
            Pozicio hajoPoz = jatekos.getHajo().getPozicio();
            if (jatekosPoz != null) {
                terkep[jatekosPoz.getSorPozicio()][jatekosPoz.getOszlopPozicio()] |= TerkepKodokUtil.MASIK_JATEKOS;
            }
            if (hajoPoz != null) {
                int sor = hajoPoz.getSorPozicio();
                int oszlop = hajoPoz.getOszlopPozicio();
                terkep[sor][oszlop] |= TerkepKodokUtil.HAJO;
                terkep[sor + 1][oszlop] |= TerkepKodokUtil.HAJO;
                terkep[sor][oszlop + 1] |= TerkepKodokUtil.HAJO;
                terkep[sor + 1][oszlop + 1] |= TerkepKodokUtil.HAJO;

                if (jatekos.getHajo().viztisztito()) {
                    terkep[sor][oszlop] |= TerkepKodokUtil.VIZTISZTITO;
                }
                if (jatekos.getHajo().tuzhely()) {
                    terkep[sor][oszlop + 1] |= TerkepKodokUtil.TUZHELY;
                }
            }

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
    public void inputKezeles(String irany, Jatekos jatekos) {
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
