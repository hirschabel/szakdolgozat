package hu.szakdolgozat.controller;

import hu.szakdolgozat.szerver_kapcsolat.SzerverKapcsolat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BejelentkezesController implements ActionListener {
    private SzerverKapcsolat kapcsolat;

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "login" -> {
                System.out.println("Login");
                kapcsolat = new SzerverKapcsolat();
                kapcsolat.csatlakozas("127.0.0.1", 52564, "admin", "admin");
            }
            case "kuldes" -> {
                kapcsolat.uzenetKuld("Elso");
            }
            case "kuldes2" -> {
                kapcsolat.uzenetKuld("Masodik");
            }
        }
    }
}
