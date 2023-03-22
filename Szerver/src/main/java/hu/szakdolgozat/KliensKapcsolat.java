package hu.szakdolgozat;

import hu.szakdolgozat.dao.JatekosDao;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

public class KliensKapcsolat implements Runnable {
    private final int HATAR_SOR = 9;
    private final int HATAR_OSZLOP = 9;
    private final Szerver szerver;
    private final Csatlakozas csatlakozas;
    private String jatekosNev;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private boolean connected;
    private final JatekAdatLista jatekAdatLista;

    public KliensKapcsolat(Szerver szerver, Csatlakozas csatlakozas,
                           JatekAdatLista jatekAdatLista) throws IOException {
        this.csatlakozas = csatlakozas;
        this.jatekAdatLista = jatekAdatLista;
        this.szerver = szerver;

        try {
            input = new ObjectInputStream(csatlakozas.getKliens().getInputStream());
            output = new ObjectOutputStream(csatlakozas.getKliens().getOutputStream());

            String bejelentkezoAdatok = (String) input.readObject();
            String[] felhasznalo = new String[]{
                    bejelentkezoAdatok.split(";")[0],
                    bejelentkezoAdatok.split(";")[1]
            };

            if (new JatekosDao().jatekosLetezik(felhasznalo[0], felhasznalo[1])) {
                csatlakozas.setJatekos(new Jatekos(felhasznalo[0], new Pozicio(4, 4))); // TODO random pozicioba kezdes
                this.jatekosNev = felhasznalo[0];
                output.writeObject("Sikeres csatlakozas!");
                System.out.println("Sikeres csatlakozas");
                connected = true;
            } else {
                output.writeObject("Hibas adatok!");
                input.close();
                output.close();
                csatlakozas.getKliens().close();
                connected = false;
                szerver.deleteCsatlakozas(csatlakozas);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            connected = false;
            szerver.deleteCsatlakozas(csatlakozas);
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
                System.out.println("KILÉPETT");
                connected = false;
                szerver.deleteCsatlakozas(csatlakozas);
            }
        }).start();

        while (connected) {
            try {
                JatekAdat jatekAdat = jatekAdatLista.receive();
                int[][] teljesTerkep = jatekAdat.getTerkep();
                for (Jatekos jatekos : jatekAdat.getJatekosok()) {
                    Pozicio jatekosPoz = jatekos.getPozicio();
                    Pozicio hajoPoz = jatekos.getHajo().getPozicio();
                    if (hajoPoz != null) {
                        teljesTerkep[hajoPoz.getSorPozicio()][hajoPoz.getOszlopPozicio()] |= 0x01000000;
                        teljesTerkep[hajoPoz.getSorPozicio() + 1][hajoPoz.getOszlopPozicio()] |= 0x01000000;
                        teljesTerkep[hajoPoz.getSorPozicio()][hajoPoz.getOszlopPozicio() + 1] |= 0x01000000;
                        teljesTerkep[hajoPoz.getSorPozicio() + 1][hajoPoz.getOszlopPozicio() + 1] |= 0x01000000;
                    }
                    if (!jatekosNev.equals(jatekos.getName())) {
                        teljesTerkep[jatekosPoz.getSorPozicio()][jatekosPoz.getOszlopPozicio()] |= 0x00000010;
                    }
                }

                int[][] kisTerkep = kisTerkepSzerzes(teljesTerkep);
                output.writeObject(kisTerkep);

                // OPTIONAL
                Jatekos csatJatekos = csatlakozas.getJatekos();
                output.writeObject(new int[]{csatJatekos.getPozicio().getSorPozicio(),
                        csatJatekos.getPozicio().getOszlopPozicio()});
                output.writeObject(new int[]{
                        csatJatekos.getEszkoztar().getBotSzam(),
                        csatJatekos.getEszkoztar().getLevelSzam(),
                        csatJatekos.getEszkoztar().getUvegSzam()
                });
                // END OPTIONAL

                output.reset();
            } catch (IOException e) {
                System.out.println("outputbol torolve");
                connected = false;
                szerver.deleteCsatlakozas(csatlakozas);
            }
        }
    }

    private int[][] kisTerkepSzerzes(int[][] nagyTerkep) {
        int[][] kisTerkep = new int[10][10];
        Pozicio poz = csatlakozas.getJatekos().getPozicio();

        int jatekosSor = poz.getSorPozicio();
        int jatekosOszl = poz.getOszlopPozicio();

        int startSor = jatekosSor - (HATAR_SOR / 2);
        int startOszl = jatekosOszl - (HATAR_OSZLOP / 2);

        for (int i = 0, sor = startSor; i < HATAR_SOR; i++, sor++) {
            for (int j = 0, oszlop = startOszl; j < HATAR_OSZLOP; j++, oszlop++) {
                if (sor >= 0 && oszlop >= 0 && sor < 100 && oszlop < 100) {
                    kisTerkep[i][j] = nagyTerkep[sor][oszlop];
                }
            }
        }
        kisTerkep[HATAR_SOR / 2][HATAR_OSZLOP / 2] |= 0x00000010; // saját játékos
        return kisTerkep;
    }
}
