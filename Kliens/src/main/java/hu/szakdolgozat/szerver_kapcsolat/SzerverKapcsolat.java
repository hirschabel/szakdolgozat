package hu.szakdolgozat.szerver_kapcsolat;

import hu.szakdolgozat.Adat;
import hu.szakdolgozat.Eszkoztar;
import lombok.Getter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

public class SzerverKapcsolat {
    @Getter
    private int[][] terkep;
    @Getter
    private Eszkoztar eszkoztar;
    @Getter
    private int szint;
    @Getter
    private int[] szuksegesTargyak;
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
                    szint = adat.getSzint();
                    szuksegesTargyak = adat.getTargySzintlepeshez();
                    System.out.println("Erőforrások: " + adat.getEroforras().getElet() + " - " + adat.getEroforras().getItal() + " - " + adat.getEroforras().getEtel());
                    System.out.println("Szint: " + adat.getSzint() + " - " + Arrays.toString(adat.getTargySzintlepeshez()));
//                    System.out.println("Pozíció: " + adat.getPozicio());
                }
            } catch (IOException | ClassNotFoundException e) {
                lecsatlakozas();
            }
        }).start();
    }

    public int[][] terkepOlvas() throws IOException, ClassNotFoundException {
        return (int[][]) in.readObject();
    }

    public int[] intOlvas() throws IOException, ClassNotFoundException {
        return (int[]) in.readObject();
    }

    private void bejelentkezes(String felhasznaloNev, String jelszo) throws IOException, ClassNotFoundException {
        uzenetKuld(felhasznaloNev.concat(";").concat(jelszo));
        String input = uzenetFogad();
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

    private String uzenetFogad() throws IOException, ClassNotFoundException {
        return (String) in.readObject();
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
