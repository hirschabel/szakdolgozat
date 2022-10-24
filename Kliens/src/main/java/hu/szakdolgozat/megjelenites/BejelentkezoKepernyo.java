package hu.szakdolgozat.megjelenites;

import hu.szakdolgozat.controller.BejelentkezesController;

import javax.swing.*;
import java.awt.event.ActionListener;

public class BejelentkezoKepernyo extends Kepernyo {
    private JButton csatlakozas;
    private final ActionListener listener;

    public BejelentkezoKepernyo(int szelesseg, int magassag, Ablak ablak) {
        super(szelesseg, magassag);
        listener = new BejelentkezesController();
        ((BejelentkezesController)listener).setListener(ablak);
        komponensekLetrehozasa();
        init(csatlakozas); // TODO: input mezők: init(csatlakozas, input1, input2, ...)

    }

    private void komponensekLetrehozasa() {
        csatlakozas = new JButton("Bejelentkezés");
        csatlakozas.setBounds(ABLAK_SZELESSEG / 2 - 75, ABLAK_MAGASSAG / 2 - 15, 150, 30);
        csatlakozas.addActionListener(listener);
        csatlakozas.setActionCommand("login");
    }
}
