package hu.szakdolgozat;

import hu.szakdolgozat.dao.JatekosDao;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.function.Function;

public class TesztKliensKapcsolat implements Runnable {
    private final Csatlakozas csatlakozas;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private final String[] kliensInput;
    private final Function<Csatlakozas, Boolean> torles;

    public TesztKliensKapcsolat(Csatlakozas csatlakozas, Function<Csatlakozas, Boolean> torles) {
        this.csatlakozas = csatlakozas;
        this.torles = torles;
        this.kliensInput = new String[] { "null" };
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
            } else {
                output.writeObject("Hibas adatok!");
                input.close();
                output.close();
                csatlakozas.getKliens().close();
                torles.apply(csatlakozas);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            torles.apply(csatlakozas);
        }


    }

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
}
