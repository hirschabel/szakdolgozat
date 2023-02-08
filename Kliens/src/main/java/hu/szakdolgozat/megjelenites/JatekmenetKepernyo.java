package hu.szakdolgozat.megjelenites;

import hu.szakdolgozat.controller.JatekmenetController;
import hu.szakdolgozat.szerver_kapcsolat.SzerverKapcsolat;

import java.awt.Color;
import java.awt.Graphics;

public class JatekmenetKepernyo extends Kepernyo {
    private final int HATAR_SOR = 9;
    private final int HATAR_OSZLOP = 9;
    private int[][] terkep;

    public JatekmenetKepernyo(int szelesseg, int magassag, SzerverKapcsolat kapcsolat) {
        super(szelesseg, magassag);
        this.addKeyListener(new JatekmenetController(kapcsolat));
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
        if (terkep == null) {
            return;
        }

        for (int sor = 0; sor < HATAR_SOR; sor++) {
            for (int oszlop = 0; oszlop < HATAR_OSZLOP; oszlop++) {
                if (terkep[sor][oszlop] == 0) {
                    g.setColor(Color.LIGHT_GRAY);
                } else if (terkep[sor][oszlop] == 2) {
                    g.setColor(Color.BLUE);
                } else if (terkep[sor][oszlop] == 1) {
                    g.setColor(Color.RED);
                } else if (terkep[sor][oszlop] == -1) {
                    g.setColor(Color.magenta);
                }

                int hossz = this.ABLAK_MAGASSAG / 10;
                int x = hossz * oszlop;
                int y = hossz * sor;
                g.fillRect(x, y, hossz, hossz);
            }
        }
    }
}
