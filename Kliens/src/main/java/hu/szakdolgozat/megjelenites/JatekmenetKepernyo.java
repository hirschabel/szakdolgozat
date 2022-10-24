package hu.szakdolgozat.megjelenites;

import hu.szakdolgozat.controller.JatekmenetController;
import hu.szakdolgozat.szerver_kapcsolat.SzerverKapcsolat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.RED);
                }


                int hossz = this.ABLAK_MAGASSAG / 10;
                int x = hossz * sor;
                int y = hossz * oszlop;
                //hossz = oszlop != 9 && sor != 9 ? hossz : hossz - 10;
                g.fillRect(x, y, hossz, hossz);
            }
        }
    }
}
