package hu.szakdolgozat;

public class TerkepLista {
    private int[][] terkep;

    public synchronized int[][] receive() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return terkep;
    }

    public synchronized void send(int[][] terkep) {
        this.terkep = terkep;
        notifyAll();
    }
}
