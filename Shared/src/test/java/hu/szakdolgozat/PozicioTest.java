package hu.szakdolgozat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PozicioTest {

    @Test
    void testConstructor() {
        Pozicio pozicio = new Pozicio(3, 5);
        assertEquals(3, pozicio.getSorPozicio());
        assertEquals(5, pozicio.getOszlopPozicio());
    }

    @Test
    void testSetPozicio() {
        Pozicio pozicio = new Pozicio(0, 0);
        pozicio.setPozicio(4, 6);
        assertEquals(4, pozicio.getSorPozicio());
        assertEquals(6, pozicio.getOszlopPozicio());
    }

    @Test
    void testMozgasRelativ() {
        Pozicio pozicio = new Pozicio(2, 2);
        pozicio.mozgasRelativ(1, -1);
        assertEquals(3, pozicio.getSorPozicio());
        assertEquals(1, pozicio.getOszlopPozicio());
    }

    @Test
    void testIsRajta() {
        Pozicio pozicio1 = new Pozicio(3, 4);
        Pozicio pozicio2 = new Pozicio(3, 4);
        assertTrue(pozicio1.isRajta(pozicio2));
    }

    @Test
    void testIsNotRajta() {
        Pozicio pozicio1 = new Pozicio(0, 0);
        Pozicio pozicio2 = new Pozicio(3, 4);
        assertFalse(pozicio1.isRajta(pozicio2));
    }

    @Test
    void testIsHajon() {
        Pozicio hajoPoz = new Pozicio(1, 1);
        Pozicio pozicio1 = new Pozicio(1, 1);
        Pozicio pozicio2 = new Pozicio(2, 1);
        Pozicio pozicio3 = new Pozicio(1, 2);
        Pozicio pozicio4 = new Pozicio(2, 2);
        assertTrue(pozicio1.isHajon(hajoPoz));
        assertTrue(pozicio2.isHajon(hajoPoz));
        assertTrue(pozicio3.isHajon(hajoPoz));
        assertTrue(pozicio4.isHajon(hajoPoz));
    }

    @Test
    void testIsNotHajon() {
        Pozicio hajoPoz = new Pozicio(1, 1);
        Pozicio pozicio = new Pozicio(0, 1);
        assertFalse(pozicio.isHajon(hajoPoz));
    }

    @Test
    void testRandomizalas() {
        Pozicio pozicio = new Pozicio(0, 0);
        pozicio.randomizalas();
        assertTrue(pozicio.getSorPozicio() >= 0 && pozicio.getSorPozicio() < 100);
        assertTrue(pozicio.getOszlopPozicio() >= 0 && pozicio.getOszlopPozicio() < 100);
    }
}
