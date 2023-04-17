package hu.szakdolgozat.szerver_kapcsolat;

import hu.szakdolgozat.Adat;
import hu.szakdolgozat.Eroforras;
import hu.szakdolgozat.Eszkoztar;
import hu.szakdolgozat.Pozicio;
import lombok.Getter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

public class SzerverKapcsolat {
    @Getter
    private long[][] terkep;
    @Getter
    private Eszkoztar eszkoztar;
    @Getter
    private int szint;
    @Getter
    private int[] szuksegesTargyak;
    @Getter
    private Eroforras eroforras;
    @Getter
    private Pozicio pozicio;
    private Socket szerver;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private boolean csatlakozva;

    public boolean csatlakozas(String ip, int port, String felhasznaloNev, String jelszo) {
        try {
            szerver = new Socket(ip, port);
            out = new ObjectOutputStream(szerver.getOutputStream());
            in = new ObjectInputStream(szerver.getInputStream());
            bejelentkezes(felhasznaloNev, jelszo);
            eszkoztar = new Eszkoztar(0, 0, 0);
            eroforras = new Eroforras(0, 0, 0);
            pozicio = new Pozicio(0, 0);
            csatlakozva = true;
            return true;
        } catch (ClassNotFoundException | IOException e) {
            return false;
        }
    }

    public void inputHallgatas() {
        new Thread(() -> {
            try {
                while (csatlakozva) {
                    Adat adat = (Adat) in.readObject();
                    terkep = adat.getTerkep();
                    eszkoztar = adat.getEszkoztar();
                    eroforras = adat.getEroforras();
                    pozicio = adat.getPozicio();
                    szint = adat.getSzint();
                    szuksegesTargyak = adat.getTargySzintlepeshez();
                }
            } catch (IOException | ClassNotFoundException e) {
                lecsatlakozas();
            }
        }).start();
    }

    private void bejelentkezes(String felhasznaloNev, String jelszo) throws IOException, ClassNotFoundException {
        uzenetKuld(felhasznaloNev.concat(";").concat(jelszo));
        String input = (String) in.readObject();
        if ("Hibas adatok!".equals(input)) {
            lecsatlakozas();
            throw new IOException("Hibas bejelentkezesi adatok!");
        }
    }

    public void uzenetKuld(String msg) {
        try {
            out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lecsatlakozas() {
        try {
            in.close();
            out.close();
            csatlakozva = false;
            szerver.close();
            //System.out.println("LECSATLAKOZVA");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
