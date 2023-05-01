package hu.szakdolgozat.megjelenites;

import hu.szakdolgozat.kommunikacio.SzerverKapcsolat;

import javax.swing.JFrame;
import java.awt.*;

public class Ablak extends JFrame {
    private static final int ABLAK_SZELESSEG = 800;
    private static final int ABLAK_MAGASSAG = 800;
    private Kepernyo kepernyo;

    public Ablak() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setPreferredSize(new Dimension(ABLAK_SZELESSEG, ABLAK_MAGASSAG));
        this.setLayout(null);
        this.pack();
        this.setLocationRelativeTo(null);

        kepernyo = new BejelentkezoKepernyo(ABLAK_SZELESSEG, ABLAK_MAGASSAG, this);
        this.add(kepernyo);
    }

    public void jatekmenetMegjelenites(SzerverKapcsolat kapcsolat) {
        System.out.println("---jatekmenet megjelenites---");
        megjelenit(new JatekmenetKepernyo(ABLAK_SZELESSEG, ABLAK_MAGASSAG, kapcsolat));
    }

    private void megjelenit(Kepernyo kepernyo) {
        this.remove(this.kepernyo);
        this.kepernyo = kepernyo;
        this.add(kepernyo);
        this.revalidate();
        this.repaint();
        this.kepernyo.setFocusable(true);
        this.kepernyo.requestFocusInWindow();
    }
}
