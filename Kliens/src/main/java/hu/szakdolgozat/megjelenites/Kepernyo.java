package hu.szakdolgozat.megjelenites;

import javax.swing.*;

public abstract class Kepernyo extends JPanel {
    protected final int ABLAK_SZELESSEG;
    protected final int ABLAK_MAGASSAG;

    public Kepernyo(int szelesseg, int magassag) {
        this.ABLAK_SZELESSEG = szelesseg;
        this.ABLAK_MAGASSAG = magassag;
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
