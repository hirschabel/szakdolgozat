package hu.szakdolgozat.adatok;

public class JatekAdatLista {
    private JatekAdat jatekAdat;

    public synchronized JatekAdat receive() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        return jatekAdat;
    }

    public synchronized void send(JatekAdat jatekAdat) {
        this.jatekAdat = jatekAdat;
        notifyAll();
    }
}
