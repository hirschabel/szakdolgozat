package hu.szakdolgozat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Szerver {
    //------------
    private Jatekos[] jatekosok = {
      new Jatekos(1, "admin", "admin"),
      new Jatekos(2, "joe", "joe"),
      new Jatekos(3, "felhasznalo", "123")
    };
    //------------
    private final int SZERVER_PORT = 52564;
    private ServerSocket szerver;
    private int jatekosSzam;
    private String[] clientInput;

    public Szerver() {
        setup();
    }

    public void setup() {
        System.out.println("---Szerver---");
        jatekosSzam = 0;
        try {
            szerver = new ServerSocket(SZERVER_PORT);
            csatlakozasFogadas();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void csatlakozasFogadas() throws IOException { // TODO: játékosszám maximum elérése után, ha lecsatlakozik valaki, akkor is fogadjon
        while (jatekosSzam < 10) {
            Socket kliens = szerver.accept();
            jatekosSzam++;
            Thread t = new Thread(new KliensKapcsolat(kliens));
            t.start();
        }
    }

    private class KliensKapcsolat implements Runnable {
        private Jatekos jatekos;

        private int sor, oszlop;
        private final Socket kliens;
        //private PrintWriter output;
        private ObjectOutputStream output;
        private BufferedReader input;

        private boolean csatlakozva;
        private int[][] terkep2 = new int[][]{
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

        private int[][] terkep;

        private KliensKapcsolat(Socket kliens) {
            this.kliens = kliens;
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
                        clientInput[0] = input.readLine();
                        log(clientInput[0], 3);
                        // TODO: KLIENS INPUT KEZELÉS
                        //inputKezeles(clientInput[0]);
                    }
                } catch (IOException e) {
                    stop(true);
                }
            }).start();
        }

        private void inputKezeles(String input) {
            System.out.println("input: " + input);
            switch (input) {
                case "W" -> mozgas(0, -1);
                case "D" -> mozgas(1, 0);
                case "A" -> mozgas(-1, 0);
                case "S" -> mozgas(0, 1);
                default -> {
                    return;
                }
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
                input = new BufferedReader(new InputStreamReader(kliens.getInputStream()));
                //output = new PrintWriter(kliens.getOutputStream(), true);
                output = new ObjectOutputStream(kliens.getOutputStream());

                String adatok = input.readLine();
                String[] felhasznalo = new String[]{adatok.split(";")[0], adatok.split(";")[1]};

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
                jatekosSzam--;
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

    public static void main(String[] args) {
        new Szerver();
    }
}
