package hu.szakdolgozat.szerver_kapcsolat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SzerverKapcsolat {
    private Socket szerver;
    private PrintWriter out;
    private BufferedReader in;

    private boolean csatlakozva;

    public boolean csatlakozas(String ip, int port, String felhasznaloNev, String jelszo) {
        try {
            szerver = new Socket(ip, port);
            out = new PrintWriter(szerver.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(szerver.getInputStream()));
            // BEJELENTKEZÉS
            bejelentkezes(felhasznaloNev, jelszo);

            csatlakozva = true;
            new Thread(() -> {
                try {
                    while (csatlakozva) {
                        String fogadottUzenet = uzenetFogad();
                        System.out.println(fogadottUzenet);
                    }
                } catch (IOException e) {
                    lecsatlakozas();
                }
            }).start();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void bejelentkezes(String felhasznaloNev, String jelszo) throws IOException {
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

    private String uzenetFogad() throws IOException {
        return in.readLine();
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
}
