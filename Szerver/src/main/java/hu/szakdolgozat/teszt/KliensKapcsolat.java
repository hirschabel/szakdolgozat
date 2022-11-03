package hu.szakdolgozat.teszt;

import hu.szakdolgozat.Jatekos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class KliensKapcsolat implements Runnable {
    private Jatekos jatekos;
    private final Socket kliens;
    //private PrintWriter output;
    private ObjectOutputStream output;
    private BufferedReader input;

    private boolean csatlakozva;
    private int[][] terkep = new int[][]{
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1}
    };

    public KliensKapcsolat(Socket kliens) {
        this.kliens = kliens;
        csatlakozva = false;
        bejelentkezes();
    }

    private void bejelentkezes() {
        try {
            input = new BufferedReader(new InputStreamReader(kliens.getInputStream()));
            //output = new PrintWriter(kliens.getOutputStream(), true);
            output = new ObjectOutputStream(kliens.getOutputStream());

            String adatok = input.readLine();
            String[] felhasznalo = new String[]{adatok.split(";")[0], adatok.split(";")[1]};

            csatlakozva = true;
            output.writeChars("Sikeres csatlakozas!");
            /*
            for (Jatekos curr : jatekosok) {
                if (curr.getName().equals(felhasznalo[0]) && curr.getPassword().equals(felhasznalo[1])) {
                    jatekos = curr;
                    log(curr.getName() + " csatlakozott!", 0);
                    csatlakozva = true;
                    output.writeChars("Sikeres csatlakozas!");
                    //output.println("Sikeres csatlakozas!");
                    return;
                }
            }

            output.writeChars("Hibas adatok!");
             */
        } catch (IOException e) {
            stop(false);
        }
    }

    @Override
    public void run() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
            if (csatlakozva) {
                try {

                    Random rnd = new Random();

                    int randomNum = ThreadLocalRandom.current().nextInt(10);
                    int randomNum2 = ThreadLocalRandom.current().nextInt(10);
                    int[][] terkep = {
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
                    terkep[randomNum][randomNum2] = 1;

                    System.out.println(Arrays.deepToString(terkep));
                    output.writeObject(terkep);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                executor.shutdown();
            }
        }, 0, 2, TimeUnit.SECONDS);
    }

    public void kuldes(int[][] terkep) throws IOException {
        output.writeObject(terkep);
        System.out.println("SZERVER:\t" + Arrays.deepToString(terkep));
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
        } catch (IOException ex) {
            ex.printStackTrace();
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
