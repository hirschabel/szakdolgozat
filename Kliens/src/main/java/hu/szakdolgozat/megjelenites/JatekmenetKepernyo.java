package hu.szakdolgozat.megjelenites;

import hu.szakdolgozat.Eszkoztar;
import hu.szakdolgozat.Kepek;
import hu.szakdolgozat.TerkepKod;
import hu.szakdolgozat.controller.JatekmenetController;
import hu.szakdolgozat.szerver_kapcsolat.SzerverKapcsolat;

import javax.swing.*;
import java.awt.*;

public class JatekmenetKepernyo extends Kepernyo {
    private static final int HATAR_SOR = 9;
    private static final int HATAR_OSZLOP = 9;
    private int[][] terkep;
    private Eszkoztar eszkoztar;
    private int szint;
    private int[] szuksegesTargyak;

    private JLabel szintMegjelenito;
    private JLabel botSzamlalo;
    private JLabel levelSzamlalo;
    private JLabel uvegSzamlalo;


    public JatekmenetKepernyo(int szelesseg, int magassag, SzerverKapcsolat kapcsolat) {
        super(szelesseg, magassag);
        this.addKeyListener(new JatekmenetController(kapcsolat));
        komponensekLetrehozasa();
        init(botSzamlalo, levelSzamlalo, uvegSzamlalo, szintMegjelenito);
        this.repaint();

        new Thread(() -> {
            while (true) {
                terkep = kapcsolat.getTerkep();
                eszkoztar = kapcsolat.getEszkoztar();
                szint = kapcsolat.getSzint();
                szuksegesTargyak = kapcsolat.getSzuksegesTargyak();
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

        for (int sor = 0; sor < HATAR_SOR; sor++) { // TODO: enum
            for (int oszlop = 0; oszlop < HATAR_OSZLOP; oszlop++) {
                int[] templates = new int[]{
                        TerkepKod.TERKEP_MEZO, // térkép mező
                        TerkepKod.BOT, // bot
                        TerkepKod.LEVEL, // levél
                        TerkepKod.UVEG, // üveg
                        TerkepKod.HAJO, // hajó
                        TerkepKod.CAPA,  // cápa
                        TerkepKod.SAJAT_JATEKOS, // másik játékos
                        TerkepKod.MASIK_JATEKOS // saját tátékos
                };

                int hossz = this.ABLAK_MAGASSAG / 9;
                int x = hossz * oszlop;
                int y = hossz * sor;

                boolean terkepenKivul = true;
                for (int template : templates) {
                    if ((terkep[sor][oszlop] & template) != 0x00000000) {
                        terkepenKivul = false;
                        g.drawImage(Kepek.findImage(template), x, y, hossz, hossz, null);
                    }
                }
                if (terkepenKivul) {
                    g.drawImage(Kepek.TERKEPEN_KIVUL, x, y, hossz, hossz, null);
                }
            }
        }
    }

    private void updateLabels() {
        levelSzamlalo.setText("Levél: " + eszkoztar.getLevelSzam() + " / " + szuksegesTargyak[0]);
        botSzamlalo.setText("Bot: " + eszkoztar.getBotSzam() + " / " + szuksegesTargyak[1]);
        uvegSzamlalo.setText("Üveg: " + eszkoztar.getUvegSzam() + " / " + szuksegesTargyak[2]);
        szintMegjelenito.setText("SZINT: " + szint);
    }

    private void komponensekLetrehozasa() {
        levelSzamlalo = new JLabel("Levél: 0");
        levelSzamlalo.setBounds(10, 10, 100, 30);
        botSzamlalo = new JLabel("Bot: 0");
        botSzamlalo.setBounds(10, 50, 100, 30);
        uvegSzamlalo = new JLabel("Üveg: 0");
        uvegSzamlalo.setBounds(10, 90, 100, 30);
        szintMegjelenito = new JLabel("SZINT: 0", SwingConstants.CENTER);
        szintMegjelenito.setBounds(800 / 2 - 100, 10, 200, 30);
    }
}
