package hu.szakdolgozat.szerver_kapcsolat;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class SzerverKapcsolat {
    private int[][] terkep;

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
                    //int[][] tempTerkep = (int[][])in.readObject();
                    //System.out.println("KLIENS:\t\t" + Arrays.deepToString(tempTerkep));
                    //terkep = tempTerkep;
                    terkep = terkepOlvas();
                    System.out.println("Olvasva");
                    //System.out.println("KLIENS:\t\t" + Arrays.deepToString(terkep));
                }
            } catch (IOException | ClassNotFoundException e) {
                lecsatlakozas();
            }
        }).start();
    }

    public int[][] terkepOlvas() throws IOException, ClassNotFoundException {
        return (int[][])in.readObject();
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

    public boolean lecsatlakozas() {
        try {
            in.close();
            out.close();
            csatlakozva = false;
            szerver.close();
            //System.out.println("LECSATLAKOZVA");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int[][] getTerkep()  {
        return terkep;
    }
}
