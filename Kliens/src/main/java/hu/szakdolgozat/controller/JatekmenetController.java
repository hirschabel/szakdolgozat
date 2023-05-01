package hu.szakdolgozat.controller;

import hu.szakdolgozat.kommunikacio.SzerverKapcsolat;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class JatekmenetController implements KeyListener {
    private final SzerverKapcsolat kapcsolat;

    public JatekmenetController(SzerverKapcsolat kapcsolat) {
        super();
        this.kapcsolat = kapcsolat;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'w', 'W' -> kapcsolat.uzenetKuld("W");
            case 'd', 'D' -> kapcsolat.uzenetKuld("D");
            case 's', 'S' -> kapcsolat.uzenetKuld("S");
            case 'a', 'A' -> kapcsolat.uzenetKuld("A");

            case 'i', 'I' -> kapcsolat.uzenetKuld("I");
            case 'l', 'L' -> kapcsolat.uzenetKuld("L");
            case 'k', 'K' -> kapcsolat.uzenetKuld("K");
            case 'j', 'J' -> kapcsolat.uzenetKuld("J");
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
