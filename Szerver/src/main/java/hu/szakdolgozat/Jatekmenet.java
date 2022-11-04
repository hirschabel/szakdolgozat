package hu.szakdolgozat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Jatekmenet implements Runnable {
    private int[][] terkep;
    private List<Csatlakozas> csatlakozasok;

    public Jatekmenet(int[][] terkep, List<Csatlakozas> csatlakozasok) {
        this.terkep = terkep;
        this.csatlakozasok = csatlakozasok;
    }

    @Override
    public void run() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {



        }, 0, 350, TimeUnit.MILLISECONDS);
    }
}
