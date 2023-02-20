package hu.szakdolgozat.szerver_kapcsolat;

import hu.szakdolgozat.Inventory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SzerverKapcsolat {
    private int[][] terkep;
    private Inventory eszkoztar;
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
            eszkoztar = new Inventory(0, 0, 0);
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
                    terkep = terkepOlvas();
                    int[] jatekosPoz = intOlvas();
                    int[] alapanyagok = intOlvas();
                    eszkoztar.setTargyak(alapanyagok[0], alapanyagok[1], alapanyagok[2]);
                    System.out.println("Olvasva (" + jatekosPoz[0] + "," + jatekosPoz[1] + ")");
                }
            } catch (IOException | ClassNotFoundException e) {
                lecsatlakozas();
            }
        }).start();
    }

    public int[][] terkepOlvas() throws IOException, ClassNotFoundException {
        return (int[][])in.readObject();
    }

    public int[] intOlvas() throws IOException, ClassNotFoundException {
        return (int[])in.readObject();
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

    public int[][] getTerkep()  {
        return terkep;
    }

    public Inventory getEszkoztar() {
        return eszkoztar;
    }
}
