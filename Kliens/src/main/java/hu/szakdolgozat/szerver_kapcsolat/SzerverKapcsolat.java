package hu.szakdolgozat.szerver_kapcsolat;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class SzerverKapcsolat {
    private int[][] terkep;

    private Socket szerver;
    private PrintWriter out;
    private ObjectInputStream in;

    private boolean csatlakozva;

    public boolean csatlakozas(String ip, int port, String felhasznaloNev, String jelszo) {
        try {
            szerver = new Socket(ip, port);
            out = new PrintWriter(szerver.getOutputStream(), true);
            in = new ObjectInputStream(szerver.getInputStream());
            bejelentkezes(felhasznaloNev, jelszo);

            csatlakozva = true;
            new Thread(() -> {
                try {
                    while (csatlakozva) {
                        int[][] tempTerkep = (int[][])in.readObject();
                        System.out.println("KLIENS:\t\t" + Arrays.deepToString(tempTerkep));
                        terkep = tempTerkep;
                    }
                } catch (IOException | ClassNotFoundException e) {
                    lecsatlakozas();
                }
            }).start();
            return true;
        } catch (ClassNotFoundException | IOException e) {
            return false;
        }
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
        out.println(msg);
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
            System.out.println("LECSATLAKOZVA");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[][] getTerkep()  {
        return terkep;
    }
}
