package hu.szakdolgozat;

import hu.szakdolgozat.dao.JatekosDao;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class KliensKapcsolat implements Runnable {
    private Jatekos jatekos;

    private int sor, oszlop;
    private final Socket kliens;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String[] clientInput;
    private boolean csatlakozva;
    private Runnable jatekosSzamCsokkent;

    private int[][] terkep;

    public KliensKapcsolat(Socket kliens, Runnable jatekosSzamCsokkent) {
        this.kliens = kliens;
        this.jatekosSzamCsokkent = jatekosSzamCsokkent;
        csatlakozva = false;
        clientInput = new String[] { "null" };
        sor = oszlop = 4;
        terkep = new int[][] {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        terkep[sor][oszlop] = 1;


        bejelentkezes();

        new Thread(() -> {
            try {
                while (csatlakozva) {
                    clientInput[0] = (String) input.readObject();
                    log(clientInput[0], 3);
                }
            } catch (IOException | ClassNotFoundException e) {
                stop(true);
            }
        }).start();
    }

    private void inputKezeles(String input) {
        System.out.println("input: " + input);
        switch (input) {
            case "W" -> mozgas(-1, 0);
            case "D" -> mozgas(0, 1);
            case "A" -> mozgas(0, -1);
            case "S" -> mozgas(1, 0);
            default -> {}
        }
    }

    private void mozgas(int sorDiff, int oszlopDiff) {
        if (mozoghatOda(sor + sorDiff, oszlop + oszlopDiff)) {
            terkep[sor][oszlop] = 0;
            sor = sor + sorDiff;
            oszlop = oszlop + oszlopDiff;
            terkep[sor][oszlop] = 1;
        }
    }

    private boolean mozoghatOda(int x, int y) {
        return x >= 0 && y >= 0 && x < 10 && y < 10;
    }


    private void bejelentkezes() {
        try {
            input = new ObjectInputStream(kliens.getInputStream());
            output = new ObjectOutputStream(kliens.getOutputStream());

            String adatok = (String) input.readObject();
            String[] felhasznalo = new String[]{adatok.split(";")[0], adatok.split(";")[1]};


            if (new JatekosDao().jatekosLetezik(felhasznalo[0], felhasznalo[1])) {
                jatekos = new Jatekos(felhasznalo[0], felhasznalo[1]);
                log(jatekos.getName() + " csatlakozott!", 0);
                csatlakozva = true;
                output.writeObject("Sikeres csatlakozas!");
                return;
            }
            output.writeObject("Hibas adatok!");
            stop(false);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            stop(false);
        }
    }

    @Override
    public void run() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
            if (csatlakozva) {
                try {
                    inputKezeles(clientInput[0]);
                    output.writeObject(terkep.clone());
                    output.reset();
                    clientInput[0] = "null";
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                executor.shutdown();
            }
        }, 0, 350, TimeUnit.MILLISECONDS);
    }

    private void stop(boolean lecsatlakozas) {
        try {
            if (lecsatlakozas) {
                log(jatekos.getName() + " lecsatlakozott!", 0);
            } else {
                log("Csatlakozas sikertelen!", 2);
            }
            input.close();
            output.close();
            kliens.close();
            csatlakozva = false;
            jatekosSzamCsokkent.run();
        } catch (IOException ignored) {
        }
    }

    private void log(String szoveg, int csatorna) {
        String csatornaSzoveg = switch (csatorna) {
            case 0 -> "[LOG]";
            case 1 -> "[SZERVER->" + jatekos.getName() + "]";
            case 2 -> "[ERROR]";
            case 3 -> "[SZERVER<-" + jatekos.getName() + "]";
            default -> "[UNKNOWN]";
        };
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println(dtf.format(LocalDateTime.now()) + " " + csatornaSzoveg + " " + szoveg);
    }
}