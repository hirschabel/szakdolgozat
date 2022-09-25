import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Kliens {
    private Socket kliens;
    private PrintWriter out;
    private BufferedReader in;

    public void csatlakozas(String ip, int port) {
        try {
            kliens = new Socket(ip, port);
            out = new PrintWriter(kliens.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kliens.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String uzenetKuldes(String msg) {
        String resp = null;
        try {
            resp = in.readLine();
            out.println(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resp;
    }

    public void lecsatlakozas() {
        try {
            in.close();
            out.close();
            kliens.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
