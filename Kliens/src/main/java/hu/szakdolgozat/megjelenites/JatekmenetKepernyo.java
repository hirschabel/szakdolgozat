package hu.szakdolgozat.megjelenites;

import hu.szakdolgozat.controller.JatekmenetController;
import hu.szakdolgozat.szerver_kapcsolat.SzerverKapcsolat;

import java.awt.*;
import java.awt.event.KeyListener;

public class JatekmenetKepernyo extends Kepernyo {
    private final KeyListener keyListener;
    private final SzerverKapcsolat kapcsolat;

    private int[][] terkep; //TODO:TÉRKÉP

    public JatekmenetKepernyo(int szelesseg, int magassag, SzerverKapcsolat kapcsolat) {
        super(szelesseg, magassag);
        keyListener = new JatekmenetController(kapcsolat);
        this.addKeyListener(keyListener);
        this.kapcsolat = kapcsolat;

        init();


        this.repaint();
        new Thread(() -> {
            while (true) {
                terkep = kapcsolat.getTerkep();
                repaint();
            }
        }).start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //terkep = kapcsolat.getTerkep();
        if (terkep == null) {
            return;
        }

        for (int sor = 0; sor < 10; sor++) {
            for (int oszlop = 0; oszlop < 10; oszlop++) {
                if (terkep[sor][oszlop] == 0) {
                    g.setColor(Color.LIGHT_GRAY);
                } else if (terkep[sor][oszlop] == 2) {
                    g.setColor(Color.BLUE);
                } else if (terkep[sor][oszlop] == 1) {
                    g.setColor(Color.RED);
                }


                int hossz = this.ABLAK_MAGASSAG / 10;
                int x = hossz * oszlop;
                int y = hossz * sor;
                //hossz = oszlop != 9 && sor != 9 ? hossz : hossz - 10;
                g.fillRect(x, y, hossz, hossz);
            }
        }
    }
}
