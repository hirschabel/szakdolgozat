package hu.szakdolgozat;

import hu.szakdolgozat.dao.JatekosDao;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

public class TesztKliensKapcsolat implements Runnable {
    private final Csatlakozas csatlakozas;
    //private final String jatekosNev;
    private final Function<Csatlakozas, Boolean> torles;
    private final JatekosLista jatekosLista;
    private final TerkepLista terkepLista;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private boolean connected;

    public TesztKliensKapcsolat(Csatlakozas csatlakozas, Function<Csatlakozas, Boolean> torles, JatekosLista jatekosLista,
                                    TerkepLista terkepLista) {
        this.csatlakozas = csatlakozas;
        //this.jatekosNev = "";
        this.torles = torles;
        this.jatekosLista = jatekosLista;
        this.terkepLista = terkepLista;
        try {
            input = new ObjectInputStream(csatlakozas.getKliens().getInputStream());
            output = new ObjectOutputStream(csatlakozas.getKliens().getOutputStream());

            String bejelentkezoAdatok = (String) input.readObject();
            String[] felhasznalo = new String[]{
                    bejelentkezoAdatok.split(";")[0],
                    bejelentkezoAdatok.split(";")[1]
            };

            if (new JatekosDao().jatekosLetezik(felhasznalo[0], felhasznalo[1])) {
                csatlakozas.setJatekos(new Jatekos(felhasznalo[0], new Pozicio(4, 4)));
                //this.jatekosNev = felhasznalo[0];
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

    @Override
    public void run() {
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
                int hatarSor = 9, hatarOszlop = 9;
                int[][] kapottTerkep = terkepLista.receive();
                List<Jatekos> jatekosok = jatekosLista.receive();

                this.csatlakozas.setJatekos(jatekosok.get(jatekosok.indexOf(this.csatlakozas.getJatekos())));
                int[][] kisTerkep = kisTerkepSzerzes(kapottTerkep, hatarSor, hatarOszlop);
                for (Jatekos jatekos : jatekosok) {
                    if (this.csatlakozas.getJatekos().getName().equals(jatekos.getName())) {
                        this.csatlakozas.getJatekos().setPozicio(jatekos.getPozicio());
                    }
                }
                output.writeObject(kisTerkep);
                output.writeObject(new int[] {csatlakozas.getJatekos().getPozicio().getSorPozicio(),
                    csatlakozas.getJatekos().getPozicio().getOszlopPozicio()});
                output.reset();
            } catch (IOException e) {
                System.out.println("outputbol torolve");
                connected = false;
                torles.apply(csatlakozas);
            }
        }
    }

    private int[][] kisTerkepSzerzes(int[][] nagyTerkep, int terkepSor, int terkepOszlop) {
        int[][] kisTerkep = new int[10][10];
        Pozicio poz = csatlakozas.getJatekos().getPozicio();
        for (int i = 0, sor = poz.getSorPozicio() - (terkepSor / 2); i < terkepSor; i++, sor++) {
            for(int j = 0, oszlop = poz.getOszlopPozicio() - (terkepOszlop / 2); j < terkepOszlop; j++, oszlop++) {
                if (sor < 0 || oszlop < 0 || sor >= 100 || oszlop >= 100) {
                    kisTerkep[i][j] = -1;
                    continue;
                }
                kisTerkep[i][j] = nagyTerkep[sor][oszlop];
            }
        }


        return kisTerkep;
    }
}
