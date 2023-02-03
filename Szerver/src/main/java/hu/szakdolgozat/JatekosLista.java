package hu.szakdolgozat;

import java.util.List;

public class JatekosLista {
    private List<Jatekos> jatekosok;

    public synchronized List<Jatekos> receive() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();;
        }

        return jatekosok;
    }

    public synchronized void send(List<Jatekos> jatekosok) {
        this.jatekosok = jatekosok;
        notifyAll();
    }
}
