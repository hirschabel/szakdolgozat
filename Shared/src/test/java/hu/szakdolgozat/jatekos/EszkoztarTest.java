package hu.szakdolgozat.jatekos;

import hu.szakdolgozat.TerkepKodok;
import hu.szakdolgozat.jatekos.Eszkoztar;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EszkoztarTest {

    @Test
    void testAddTargy() {
        Eszkoztar eszkoztar = new Eszkoztar(0, 0, 0);
        eszkoztar.addTargy(TerkepKodok.BOT);
        eszkoztar.addTargy(TerkepKodok.LEVEL);
        eszkoztar.addTargy(TerkepKodok.UVEG);

        assertEquals(1, eszkoztar.getBotSzam());
        assertEquals(1, eszkoztar.getLevelSzam());
        assertEquals(1, eszkoztar.getUvegSzam());
    }

    @Test
    void testSetTargyak() {
        Eszkoztar eszkoztar = new Eszkoztar(0, 0, 0);
        eszkoztar.setTargyak(3, 2, 1);

        assertEquals(3, eszkoztar.getBotSzam());
        assertEquals(2, eszkoztar.getLevelSzam());
        assertEquals(1, eszkoztar.getUvegSzam());
    }

    @Test
    void testTargyCsokkentes() {
        Eszkoztar eszkoztar = new Eszkoztar(5, 5, 5);
        int[] csokkentes = {2, 3, 1};

        boolean success = eszkoztar.targyCsokkentes(csokkentes);

        assertTrue(success);
        assertEquals(3, eszkoztar.getLevelSzam());
        assertEquals(2, eszkoztar.getBotSzam());
        assertEquals(4, eszkoztar.getUvegSzam());
    }

    @Test
    void testTargyCsokkentesNyersanyagNelkul() {
        Eszkoztar eszkoztar = new Eszkoztar(1, 1, 1);
        int[] csokkentes = {2, 3, 1};

        boolean success = eszkoztar.targyCsokkentes(csokkentes);

        assertFalse(success);
        assertEquals(1, eszkoztar.getBotSzam());
        assertEquals(1, eszkoztar.getLevelSzam());
        assertEquals(1, eszkoztar.getUvegSzam());
    }

    @Test
    void testUrites() {
        Eszkoztar eszkoztar = new Eszkoztar(3, 2, 1);
        eszkoztar.urites();

        assertEquals(0, eszkoztar.getBotSzam());
        assertEquals(0, eszkoztar.getLevelSzam());
        assertEquals(0, eszkoztar.getUvegSzam());
    }
}
