import hu.szakdolgozat.Jatekos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Szerver {
    //------------
    private Jatekos[] jatekosok = {
      new Jatekos(1, "admin", "admin"),
      new Jatekos(2, "joe", "joe"),
      new Jatekos(3, "felhasznalo", "123")
    };
    //------------
    private final int SERVER_PORT = 52564;
    private ServerSocket szerver;
    private int jatekosSzam;

    public Szerver() {
        System.out.println("---Szerver---");
        jatekosSzam = 0;
        try {
            szerver = new ServerSocket(SERVER_PORT);
            csatlakozasFogadas();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void csatlakozasFogadas() throws IOException {
        while (jatekosSzam < 10) {
            Socket kliens = szerver.accept();
            jatekosSzam++;
            KliensKapcsolat kliensKapcsolat = new KliensKapcsolat(kliens);
            Thread t = new Thread(kliensKapcsolat);
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
            //log("Jatekos #" + jatekosSzam + " csatlakozott!", 0);
            try {
                in = new BufferedReader(new InputStreamReader(kliens.getInputStream()));
                out = new PrintWriter(kliens.getOutputStream(), true);
                bejelentkezes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void bejelentkezes() throws IOException{
            String adatok = in.readLine();
            String felhasznaloNev = adatok.split(";")[0];
            String jelszo = adatok.split(";")[1];

            for (Jatekos curr : jatekosok) {
                if (curr.megegyezik(felhasznaloNev, jelszo)) {
                    jatekos = curr;
                    log(curr.getName() + " csatlakozott!", 0);
                    return;
                }
            }
            out.println("Rossz adatok!");
        }


        @Override
        public void run() {
            try {
                int szamlalo = 0; // TESTING ONLY
                while(true) {
                    out.println(szamlalo);
                    log(in.readLine(), 3);
                    szamlalo++;
                    //todo stop when exception
                }
            } catch (IOException e) {
                try {
                    stop();
                    log("Jatekos #" + jatekosSzam + " lecsatlakozott!", 0);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        private void stop() throws IOException {
            in.close();
            out.close();
            kliens.close();
            jatekosSzam--;
        }

        private void log(String szoveg, int csatorna) {
            String csatornaSzoveg = switch (csatorna) {
                case 0 -> "[LOG]";
                case 1 -> "[SZERVER]";
                case 2 -> "[ERROR]";
                case 3 -> "[" + kliens.toString() + "]";
                default -> "[UNKNOWN]";
            };
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            System.out.println(dtf.format(now) + csatornaSzoveg + szoveg);
        }
    }

    public static void main(String[] args) {
        new Szerver();
    }
}
