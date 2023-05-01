package hu.szakdolgozat.megjelenites;

import hu.szakdolgozat.*;
import hu.szakdolgozat.util.KepUtil;
import hu.szakdolgozat.adatok.Adat;
import hu.szakdolgozat.controller.JatekmenetController;
import hu.szakdolgozat.jatekos.Eroforras;
import hu.szakdolgozat.jatekos.Eszkoztar;
import hu.szakdolgozat.kommunikacio.SzerverKapcsolat;

import javax.swing.*;
import java.awt.*;

public class JatekmenetKepernyo extends Kepernyo {
    private static final int HATAR_SOR = 9;
    private static final int HATAR_OSZLOP = 9;
    private long[][] terkep;
    private Eszkoztar eszkoztar;
    private Eroforras eroforras;
    private int szint;
    private int[] szuksegesTargyak;
    private Pozicio pozicio;

    private JLabel szintMegjelenito;
    private JLabel botSzamlalo;
    private JLabel levelSzamlalo;
    private JLabel uvegSzamlalo;
    private JLabel elet;
    private JLabel ital;
    private JLabel etel;
    private JLabel vonal;
    private JLabel pozicioMegjelenito;


    public JatekmenetKepernyo(int szelesseg, int magassag, SzerverKapcsolat kapcsolat) {
        super(szelesseg, magassag);
        this.addKeyListener(new JatekmenetController(kapcsolat));
        komponensekLetrehozasa();
        init(botSzamlalo, levelSzamlalo, uvegSzamlalo, szintMegjelenito, elet, ital, etel, vonal, pozicioMegjelenito);
        this.repaint();

        new Thread(() -> {
            while (true) {
                Adat adat = kapcsolat.getAdat();
                terkep = adat.getTerkep();
                eszkoztar = adat.getEszkoztar();
                eroforras = adat.getEroforras();
                pozicio = adat.getPozicio();
                szint = adat.getSzint();
                szuksegesTargyak = adat.getTargySzintlepeshez();

                repaint();
            }
        }).start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (terkep == null) {
            return;
        }
        updateLabels();

        for (int sor = 0; sor < HATAR_SOR; sor++) {
            for (int oszlop = 0; oszlop < HATAR_OSZLOP; oszlop++) {
                long[] templates = new long[]{
                        TerkepKodok.TERKEP_MEZO,
                        TerkepKodok.BOT,
                        TerkepKodok.LEVEL,
                        TerkepKodok.UVEG,
                        TerkepKodok.HAJO,
                        TerkepKodok.VIZTISZTITO,
                        TerkepKodok.TUZHELY,
                        TerkepKodok.CAPA,
                        TerkepKodok.SAJAT_JATEKOS,
                        TerkepKodok.MASIK_JATEKOS
                };

                int hossz = this.ABLAK_MAGASSAG / 9;
                int x = hossz * oszlop;
                int y = hossz * sor;

                boolean terkepenKivul = true;
                for (long template : templates) {
                    if ((terkep[sor][oszlop] & template) != 0x0000000000L) {
                        terkepenKivul = false;
                        g.drawImage(KepUtil.findImage(template), x, y, hossz, hossz, null);
                    }
                }
                if (terkepenKivul) {
                    g.drawImage(KepUtil.terkepenKivul(), x, y, hossz, hossz, null);
                }
            }
        }
    }

    private void updateLabels() {
        elet.setText("Élet: " + eroforras.getElet() + " / " + eroforras.getMax_elet());
        ital.setText("Ital: " + eroforras.getItal() + " / " + eroforras.getMax_ital());
        etel.setText("Étel: " + eroforras.getEtel() + " / " + eroforras.getMax_etel());
        levelSzamlalo.setText("Levél: " + eszkoztar.getLevelSzam() + " / " + szuksegesTargyak[0]);
        botSzamlalo.setText("Bot: " + eszkoztar.getBotSzam() + " / " + szuksegesTargyak[1]);
        uvegSzamlalo.setText("Üveg: " + eszkoztar.getUvegSzam() + " / " + szuksegesTargyak[2]);
        szintMegjelenito.setText("SZINT: " + szint);
        pozicioMegjelenito.setText("[" + pozicio.getSorPozicio() + "," + pozicio.getOszlopPozicio() + "]");
    }

    private void komponensekLetrehozasa() {
        elet = new JLabel("Élet: 0 / 100");
        elet.setBounds(10, 10, 100, 30);
        ital = new JLabel("Ital: 0 / 100");
        ital.setBounds(10, 30, 100, 30);
        etel = new JLabel("Étel: 0 / 100");
        etel.setBounds(10, 50, 100, 30);
        vonal = new JLabel("---------------");
        vonal.setBounds(10, 70, 100, 30);
        levelSzamlalo = new JLabel("Levél: 0");
        levelSzamlalo.setBounds(10, 10 + 80, 100, 30);
        botSzamlalo = new JLabel("Bot: 0");
        botSzamlalo.setBounds(10, 30 + 80, 100, 30);
        uvegSzamlalo = new JLabel("Üveg: 0");
        uvegSzamlalo.setBounds(10, 50 + 80, 100, 30);
        szintMegjelenito = new JLabel("SZINT: 0", SwingConstants.CENTER);
        szintMegjelenito.setBounds(800 / 2 - 100, 10, 200, 30);
        pozicioMegjelenito = new JLabel("[0,0]", SwingConstants.CENTER);
        pozicioMegjelenito.setBounds(800 / 2 - 100, 760, 200, 30);
    }
}
