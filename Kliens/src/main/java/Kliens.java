import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Kliens {
    private Socket szerver;
    private PrintWriter out;
    private BufferedReader in;

    public boolean csatlakozas(String ip, int port, String felhasznaloNev, String jelszo) {
        try {
            szerver = new Socket(ip, port);
            out = new PrintWriter(szerver.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(szerver.getInputStream()));
            if ("Csatlakozas sikertelen".equals(uzenetKuldes(felhasznaloNev.concat(";").concat(jelszo)))) {
                lecsatlakozas();
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
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
