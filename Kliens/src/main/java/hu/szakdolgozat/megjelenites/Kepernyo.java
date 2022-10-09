package hu.szakdolgozat.megjelenites;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class Kepernyo extends JPanel {
    protected final int ABLAK_SZELESSEG;
    protected final int ABLAK_MAGASSAG;
    protected ActionListener listener;

    public Kepernyo(int szelesseg, int magassag, ActionListener listener) {
        this.ABLAK_SZELESSEG = szelesseg;
        this.ABLAK_MAGASSAG = magassag;
        this.listener = listener;
    }

    protected void init(JComponent... komponensek) {
        this.setLayout(null);
        this.setBounds(0, 0, ABLAK_SZELESSEG, ABLAK_MAGASSAG);
        if (komponensek != null) {
            for (JComponent komponens : komponensek) {
                this.add(komponens);
            }
        }
    }
}
