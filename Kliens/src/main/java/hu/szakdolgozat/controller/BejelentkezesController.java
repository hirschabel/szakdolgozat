package hu.szakdolgozat.controller;

import hu.szakdolgozat.SajatListener;
import hu.szakdolgozat.szerver_kapcsolat.SzerverKapcsolat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BejelentkezesController implements ActionListener {
    private SzerverKapcsolat kapcsolat;
    private SajatListener listener;

    public void setListener(SajatListener listener) {
        this.listener = listener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("login".equals(e.getActionCommand())) {
            kapcsolat = new SzerverKapcsolat();
            if (kapcsolat.csatlakozas("127.0.0.1", 52564, "admin", "admin")) { // TODO: input mezők felh.név és jelszó írására
                System.out.println("Login Success");

                listener.jatekmenetMegjelenites(kapcsolat);




                //TODO: kepernyo megvaltoztatasa
            } else {
                System.out.println("Login Failed") ;
                // TODO: Nem sikerült bejelentkezni
            }
        }
    }
}
