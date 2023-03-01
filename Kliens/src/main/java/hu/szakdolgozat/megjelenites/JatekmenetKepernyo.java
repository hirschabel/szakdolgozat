package hu.szakdolgozat.megjelenites;

import hu.szakdolgozat.Eszkoztar;
import hu.szakdolgozat.Kepek;
import hu.szakdolgozat.controller.JatekmenetController;
import hu.szakdolgozat.szerver_kapcsolat.SzerverKapcsolat;

import javax.swing.*;
import java.awt.*;

public class JatekmenetKepernyo extends Kepernyo {
    private final int HATAR_SOR = 9;
    private final int HATAR_OSZLOP = 9;
    private int[][] terkep;
    private Eszkoztar eszkoztar;

    private JLabel botSzamlalo;
    private JLabel levelSzamlalo;
    private JLabel uvegSzamlalo;


    public JatekmenetKepernyo(int szelesseg, int magassag, SzerverKapcsolat kapcsolat) {
        super(szelesseg, magassag);
        this.addKeyListener(new JatekmenetController(kapcsolat));
        komponensekLetrehozasa();
        init(botSzamlalo, levelSzamlalo, uvegSzamlalo);
        this.repaint();

        new Thread(() -> {
            while (true) {
                terkep = kapcsolat.getTerkep();
                eszkoztar = kapcsolat.getEszkoztar();
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
                        0x00000001, // térkép mező
                        0x00001000, // bot
                        0x00010000, // levél
                        0x00100000, // üveg
                        0x01000000, // hajó
                        0x00000100, // másik játékos
                        0x00000010, // saját tátékos
                        0x10000000  // terkepen kivul
                };

                int hossz = this.ABLAK_MAGASSAG / 10;
                int x = hossz * oszlop;
                int y = hossz * sor;

                boolean terkepenKivul = true;
                for (int template : templates) {
                    int rajzolando = terkep[sor][oszlop] & template;
                    if (rajzolando != 0x00000000) {
                        terkepenKivul = false;
                        g.drawImage(Kepek.findImage(rajzolando), x, y, hossz, hossz, null);
                    }
                }
                if (terkepenKivul) {
                    g.drawImage(Kepek.findImage(0x10000000), x, y, hossz, hossz, null);
                }
            }
        }
    }

    private void updateLabels() {
        botSzamlalo.setText("Bot: " + eszkoztar.getBotSzam());
        levelSzamlalo.setText("Levél: " + eszkoztar.getLevelSzam());
        uvegSzamlalo.setText("Üveg: " + eszkoztar.getUvegSzam());
    }

    private void komponensekLetrehozasa() {
        botSzamlalo = new JLabel("Bot: 0");
        botSzamlalo.setBounds(10, 10, 100, 30);
        levelSzamlalo = new JLabel("Levél: 0");
        levelSzamlalo.setBounds(10, 50, 100, 30);
        uvegSzamlalo = new JLabel("Üveg: 0");
        uvegSzamlalo.setBounds(10, 90, 100, 30);
    }
}
