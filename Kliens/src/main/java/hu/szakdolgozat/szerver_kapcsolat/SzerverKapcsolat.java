package hu.szakdolgozat.szerver_kapcsolat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/* TODO
    Amint a kliens megkapja a szervertől az információkat, akkor egyből küld egy null-t, és ha érzékel utána gombnyomást, akkor azt is elküldi
    A szerver mindig az utolsót nézi, ezért így mindig lesz legalább egy (a null) üzenet és ha nyom valmit, az is el lesz juttatva
 */

public class SzerverKapcsolat {
    private Socket szerver;
    private PrintWriter out;
    private BufferedReader in;

    public boolean csatlakozas(String ip, int port, String felhasznaloNev, String jelszo) {
        try {
            szerver = new Socket(ip, port);
            out = new PrintWriter(szerver.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(szerver.getInputStream()));
            // BEJELENTKEZÉS
            if ("Csatlakozas sikertelen".equals(uzenetKuldes(felhasznaloNev.concat(";").concat(jelszo)))) {
                lecsatlakozas();
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void uzenetKuld(String msg) {
        out.println(msg);
    }

    public String uzenetFogad() throws IOException {
        return in.readLine();
    }

    public String uzenetKuldes(String msg) throws IOException {
        out.println(msg);
        return in.readLine();
    }

    public void lecsatlakozas() {
        try {
            in.close();
            out.close();
            szerver.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
