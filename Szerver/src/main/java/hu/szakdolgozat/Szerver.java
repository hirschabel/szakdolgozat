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

    private void csatlakozasFogadas() throws IOException {
        while (jatekosSzam < 10) {
            Socket kliens = szerver.accept();
            jatekosSzam++;
            Thread t = new Thread(new KliensKapcsolat(kliens));
            t.start();
        }
    }

    private class KliensKapcsolat implements Runnable {
        Jatekos jatekos;
        private final Socket kliens;
        private PrintWriter out;
        private BufferedReader in;

        private KliensKapcsolat(Socket kliens) {
            this.kliens = kliens;
            try {
                in = new BufferedReader(new InputStreamReader(kliens.getInputStream()));
                out = new PrintWriter(kliens.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void bejelentkezes() throws IOException{
            String adatok = in.readLine();
            String felhasznaloNev = adatok.split(";")[0];
            String jelszo = adatok.split(";")[1];

            for (Jatekos curr : jatekosok) {
                if (curr.getName().equals(felhasznaloNev) && curr.getPassword().equals(jelszo)) {
                    jatekos = curr;
                    log(curr.getName() + " csatlakozott!", 0);
                    out.println("Sikeres csatlakozas");
                    return;
                }
            }
            out.println("Csatlakozas sikertelen");
            throw new IOException("Csatlakozas sikertelen");
        }

        @Override
        public void run() {
            try {
                bejelentkezes();
                final String[] kuldendoSzoveg = {"nem nyomtál semmit"};

                ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
                executor.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // beolvasni kliens inputot
                            // számításokat végezni
                            // visszaadni a játék állapotot

                            String bejovo = in.readLine();
                            log(bejovo, 3);

                            String szovegAmitKuldVissza = "null".equals(bejovo) ? "nem nyomtal semmit" : bejovo;
                            out.println(szovegAmitKuldVissza);
                            log(szovegAmitKuldVissza, 1);

//                            out.println(kuldendoSzoveg[0]);
//                            log(kuldendoSzoveg[0],1 );
//                            String bejovo = in.readLine();
//                            kuldendoSzoveg[0] = "null".equals(bejovo) ? "nem nyomtál semmit" : bejovo;
//                            log(bejovo, 3);
                        } catch (IOException ex) {
                            executor.shutdown();
                            stop(true);
                        }
                    }
                }, 0, 5, TimeUnit.SECONDS);


            } catch (IOException e) {
                if ("Csatlakozas sikertelen".equals(e.getMessage())) {
                    log("Csatlakozas sikertelen!", 2);
                    stop(false);
                }
            }
        }



        private void stop(boolean lecsatlakozas) {
            try {
                if (lecsatlakozas) {
                    log(jatekos.getName() + " lecsatlakozott!", 0);
                }
                in.close();
                out.close();
                kliens.close();
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
