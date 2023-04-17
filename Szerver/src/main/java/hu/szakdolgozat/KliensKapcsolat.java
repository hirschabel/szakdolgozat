package hu.szakdolgozat;

import hu.szakdolgozat.capa.Capa;
import hu.szakdolgozat.dao.FelhasznaloDao;
import hu.szakdolgozat.dao.JatekosDao;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

public class KliensKapcsolat implements Runnable {
    private static final int HATAR_SOR = 9;
    private static final int HATAR_OSZLOP = 9;
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

            if (new FelhasznaloDao().jatekosLetezik(felhasznalo[0], felhasznalo[1])) {
                csatlakozas.setJatekos(new JatekosDao().getJatekos(felhasznalo[0])); // Játékos betöltése
                csatlakozas.getJatekos().setCapa(new Capa());

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
                long[][] teljesTerkep = jatekAdat.getTerkep();

                Jatekos csatJatekos = csatlakozas.getJatekos();
                Adat adat = new Adat(kisTerkepSzerzes(teljesTerkep), csatJatekos.getPozicio(), csatJatekos.getEszkoztar(), csatJatekos.getEroforrasok(),
                        csatJatekos.getHajo().getSzint(), csatJatekos.getHajo().getSzintAdat().getSzuksegesTargyak());

                output.writeObject(adat);
                output.reset();
            } catch (IOException e) {
                System.out.println("outputbol torolve");
                connected = false;
                szerver.deleteCsatlakozas(csatlakozas);
            }
        }
    }

    private long[][] kisTerkepSzerzes(long[][] nagyTerkep) {
        long[][] kisTerkep = new long[10][10];
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
        kisTerkep[HATAR_SOR / 2][HATAR_OSZLOP / 2] |= TerkepKod.SAJAT_JATEKOS;
        return kisTerkep;
    }
}
