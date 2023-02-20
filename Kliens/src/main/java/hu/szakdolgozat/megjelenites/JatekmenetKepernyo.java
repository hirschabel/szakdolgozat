package hu.szakdolgozat.megjelenites;

import hu.szakdolgozat.Inventory;
import hu.szakdolgozat.controller.JatekmenetController;
import hu.szakdolgozat.szerver_kapcsolat.SzerverKapcsolat;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Graphics;

public class JatekmenetKepernyo extends Kepernyo {
    private final int HATAR_SOR = 9;
    private final int HATAR_OSZLOP = 9;
    private int[][] terkep;
    private Inventory eszkoztar;

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
                if (terkep[sor][oszlop] == 1) { // TERKEP MEZO
                    g.setColor(Color.LIGHT_GRAY);
                } else if (terkep[sor][oszlop] == 3) { // SAJAT JATEKOS
                    g.setColor(Color.BLUE);
                } else if (terkep[sor][oszlop] == 2) { // MASIK JATEKOS
                    g.setColor(Color.RED);
                } else if (terkep[sor][oszlop] == 0) { // TERKEP KIVULI TERULET
                    g.setColor(Color.magenta);
                } else if (terkep[sor][oszlop] == 4) { // BOT
                    g.setColor(Color.PINK);
                } else if (terkep[sor][oszlop] == 5) { // LEVEL
                    g.setColor(Color.GREEN);
                } else if (terkep[sor][oszlop] == 6) { // UVEG
                    g.setColor(Color.white);
                }

                int hossz = this.ABLAK_MAGASSAG / 10;
                int x = hossz * oszlop;
                int y = hossz * sor;
                g.fillRect(x, y, hossz, hossz);
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
