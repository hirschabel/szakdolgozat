package hu.szakdolgozat.megjelenites;

import hu.szakdolgozat.controller.BejelentkezesController;

import javax.swing.*;

public class BejelentkezoKepernyo extends Kepernyo {
    private JButton csatlakozas;
    private JButton uzenetKuldes;
    private JButton uzenetKuldes2;

    public BejelentkezoKepernyo(int szelesseg, int magassag) {
        super(szelesseg, magassag, new BejelentkezesController());
        komponensekLetrehozasa();
        init(csatlakozas, uzenetKuldes, uzenetKuldes2);

    }

    private void komponensekLetrehozasa() {
        csatlakozas = new JButton("Bejelentkezés");
        csatlakozas.setBounds(ABLAK_SZELESSEG / 2 - 75, ABLAK_MAGASSAG / 2 - 15, 150, 30);
        csatlakozas.addActionListener(listener);
        csatlakozas.setActionCommand("login");

        uzenetKuldes = new JButton("kuldes");
        uzenetKuldes.setBounds(0, 0, 150, 30);
        uzenetKuldes.addActionListener(listener);
        uzenetKuldes.setActionCommand("kuldes");

        uzenetKuldes2 = new JButton("kuldes2");
        uzenetKuldes2.setBounds(155, 0, 150, 30);
        uzenetKuldes2.addActionListener(listener);
        uzenetKuldes2.setActionCommand("kuldes2");
    }
}
