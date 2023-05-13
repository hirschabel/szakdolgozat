package hu.szakdolgozat.kommunikacio;

import hu.szakdolgozat.adatok.Adat;
import lombok.Getter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SzerverKapcsolat {
    @Getter
    private Adat adat;

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
            adat = new Adat();
            csatlakozva = true;
            return true;
        } catch (ClassNotFoundException | IOException e) {
            return false;
        }
    }

    public void inputHallgatas() {
        new Thread(this::adatFogadas).start();
    }

    public void uzenetKuld(String msg) {
        try {
            out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void bejelentkezes(String felhasznaloNev, String jelszo) throws IOException, ClassNotFoundException {
        uzenetKuld(felhasznaloNev.concat(";").concat(jelszo));
        felhasznaloEllenorzes();
    }

    private void felhasznaloEllenorzes() throws IOException, ClassNotFoundException {
        String input = (String) in.readObject();
        if ("Hibas adatok!".equals(input)) {
            lecsatlakozas();
            throw new IOException("Hibas bejelentkezesi adatok!");
        }
    }

    private void adatFogadas() {
        try {
            while (csatlakozva) {
                adat = (Adat) in.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            lecsatlakozas();
        }
    }

    private void lecsatlakozas() {
        try {
            in.close();
            out.close();
            csatlakozva = false;
            szerver.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}