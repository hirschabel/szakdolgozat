package hu.szakdolgozat;

public class Terkep {
    private int[][] terkep;

    private boolean transfer = true;

    public synchronized void firstWait() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized int[][] receive() {
        while(transfer) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread Interrupted!");
            }
        }
        transfer = true;

        return terkep;
    }

    public synchronized void send(int[][] terkep) {
        this.terkep = terkep;
        transfer = false;
        notifyAll();
    }
}
