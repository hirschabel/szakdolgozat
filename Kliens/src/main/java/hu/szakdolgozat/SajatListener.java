package hu.szakdolgozat;

import hu.szakdolgozat.szerver_kapcsolat.SzerverKapcsolat;

public interface SajatListener {
    void jatekmenetMegjelenites(SzerverKapcsolat kapcsolat);

    void bejelentkezesMegjelenites();
}
