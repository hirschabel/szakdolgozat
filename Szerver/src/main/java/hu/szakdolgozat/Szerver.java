package hu.szakdolgozat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        private final Socket kliens;
        private PrintWriter output;
        private BufferedReader input;

        private boolean csatlakozva;

        private KliensKapcsolat(Socket kliens) {
            this.kliens = kliens;
            csatlakozva = true;
            clientInput = new String[] { "null" };

            bejelentkezes();

            new Thread(() -> {
                try {
                    while (csatlakozva) {
                        clientInput[0] = input.readLine();
                        log(clientInput[0], 3);
                    }
                } catch (IOException e) {
                    stop(true);
                }
            }).start();
        }

        private void bejelentkezes() {
            try {
                input = new BufferedReader(new InputStreamReader(kliens.getInputStream()));
                output = new PrintWriter(kliens.getOutputStream(), true);

                String adatok = input.readLine();
                String[] felhasznalo = new String[]{adatok.split(";")[0], adatok.split(";")[1]};

                for (Jatekos curr : jatekosok) {
                    if (curr.getName().equals(felhasznalo[0]) && curr.getPassword().equals(felhasznalo[1])) {
                        jatekos = curr;
                        log(curr.getName() + " csatlakozott!", 0);
                        output.println("Sikeres csatlakozas");
                        return;
                    }
                }
            } catch (IOException e) {
                stop(false);
            }
        }

        @Override
        public void run() {
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executor.scheduleAtFixedRate(() -> {
                if (csatlakozva) {
                    String szovegAmitKuldVissza = "null".equals(clientInput[0]) ? "nem nyomtal semmit" : clientInput[0];
                    output.println(szovegAmitKuldVissza);
                    log(szovegAmitKuldVissza, 1);
                    clientInput[0] = "null";
                } else {
                    executor.shutdown();
                }
            }, 0, 2, TimeUnit.SECONDS);
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
