package hu.szakdolgozat;

import hu.szakdolgozat.dao.JatekosDao;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.function.Function;

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
                    System.out.println(csatlakozas.getUtasitas());
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
                int[][] kisTerkep = kisTerkepSzerzes(jatekAdat.getTerkep());
                Jatekos csatJatekos = csatlakozas.getJatekos();

                for (Jatekos jatekos : jatekAdat.getJatekosok()) {
                 if (!jatekosNev.equals(jatekos.getName())) {
                     Pozicio poz = csatJatekos.getPozicio().getRelativePoz(jatekos.getPozicio(), HATAR_SOR);
                     if (poz != null) {
                         kisTerkep[poz.getSorPozicio()][poz.getOszlopPozicio()] = 3;
                     }
                 }
                }

                output.writeObject(kisTerkep);
                output.writeObject(new int[] {csatJatekos.getPozicio().getSorPozicio(),
                        csatJatekos.getPozicio().getOszlopPozicio()});
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
        for (int i = 0, sor = poz.getSorPozicio() - (HATAR_SOR / 2); i < HATAR_SOR; i++, sor++) {
            for(int j = 0, oszlop = poz.getOszlopPozicio() - (HATAR_OSZLOP / 2); j < HATAR_OSZLOP; j++, oszlop++) {
                if (sor >= 0 && oszlop >= 0 && sor < 100 && oszlop < 100) {
                    kisTerkep[i][j] = nagyTerkep[sor][oszlop];
                }
            }
        }
        kisTerkep[HATAR_SOR / 2][HATAR_OSZLOP / 2] = 2;
        return kisTerkep;
    }
}
