package hu.szakdolgozat;

import hu.szakdolgozat.dao.JatekosDao;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.function.Function;

public class TesztKliensKapcsolat implements Runnable {
    private final Csatlakozas csatlakozas;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private final String[] kliensInput;
    private final Function<Csatlakozas, Boolean> torles;
    private boolean connected;
    private Terkep terkep;

    public TesztKliensKapcsolat(Csatlakozas csatlakozas, Function<Csatlakozas, Boolean> torles, Terkep terkep) {
        this.csatlakozas = csatlakozas;
        this.torles = torles;
        this.kliensInput = new String[] { "null" };
        this.terkep = terkep;
        try {
            input = new ObjectInputStream(csatlakozas.getKliens().getInputStream());
            output = new ObjectOutputStream(csatlakozas.getKliens().getOutputStream());

            String bejelentkezoAdatok = (String) input.readObject();
            String[] felhasznalo = new String[]{
                    bejelentkezoAdatok.split(";")[0],
                    bejelentkezoAdatok.split(";")[1]
            };

            if (new JatekosDao().jatekosLetezik(felhasznalo[0], felhasznalo[1])) {
                csatlakozas.setJatekos(new Jatekos(new Pozicio(4, 4)));
                output.writeObject("Sikeres csatlakozas!");
                System.out.println("Sikeres csatlakozas");
                connected = true;
            } else {
                output.writeObject("Hibas adatok!");
                input.close();
                output.close();
                csatlakozas.getKliens().close();
                connected = false;
                torles.apply(csatlakozas);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            connected = false;
            torles.apply(csatlakozas);
        }


    }
/*
    @Override
    public void run() {
        try {
            while (true) {
                kliensInput[0] = (String) input.readObject();
                // input valtozoba iras
            }
        } catch (IOException | ClassNotFoundException e) {
            torles.apply(csatlakozas);
        }
    }
 */

    @Override
    public void run() {
        //terkep.waitFirst();
        terkep.firstWait();
        new Thread(() -> {
            try {
                while (connected) {
                    csatlakozas.setUtasitas((String) input.readObject());
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("inputbol torlve");
                connected = false;
                torles.apply(csatlakozas);
            }
        }).start();

        while (connected) {
            try {
                int[][] kapottTerkep = terkep.receive();
                output.writeObject(kapottTerkep);
                output.reset();
                System.out.println(Arrays.deepToString(kapottTerkep));
            } catch (IOException e) {
                System.out.println("outputbol torolve");
                connected = false;
                torles.apply(csatlakozas);
            }
        }
    }
}
