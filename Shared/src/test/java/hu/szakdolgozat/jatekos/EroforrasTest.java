package hu.szakdolgozat.jatekos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EroforrasTest {

    @Test
    void testSetAll() {
        Eroforras eroforras = new Eroforras(100, 100, 100);
        eroforras.setMax(50, 60, 70);

        assertEquals(50, eroforras.getMax_elet());
        assertEquals(60, eroforras.getMax_ital());
        assertEquals(70, eroforras.getMax_etel());
        assertEquals(50, eroforras.getElet());
        assertEquals(60, eroforras.getItal());
        assertEquals(70, eroforras.getEtel());
    }

    @Test
    void testEletCsokkentes() {
        Eroforras eroforras = new Eroforras(100, 100, 100);
        eroforras.eletCsokkentes(20);

        assertEquals(80, eroforras.getElet());
    }

    @Test
    void testEletCsokkentesNullara() {
        Eroforras eroforras = new Eroforras(100, 100, 100);
        eroforras.eletCsokkentes(150);

        assertEquals(0, eroforras.getElet());
    }

    @Test
    void testItalCsokkentes() {
        Eroforras eroforras = new Eroforras(100, 100, 100);
        eroforras.italCsokkentes();

        assertEquals(99, eroforras.getItal());
    }

    @Test
    void testEtelCsokkentes() {
        Eroforras eroforras = new Eroforras(100, 100, 100);
        eroforras.etelCsokkentes();

        assertEquals(99, eroforras.getEtel());
    }

    @Test
    void testItalVisszatoltes() {
        Eroforras eroforras = new Eroforras(100, 50, 100);
        eroforras.setItal(49);
        eroforras.italVisszatoltes();

        assertEquals(50, eroforras.getItal());
    }

    @Test
    void testEtelVisszatoltes() {
        Eroforras eroforras = new Eroforras(100, 100, 50);
        eroforras.setEtel(49);
        eroforras.etelVisszatoltes();

        assertEquals(50, eroforras.getEtel());
    }

    @Test
    void testEletVisszatolt() {
        Eroforras eroforras = new Eroforras(100, 100, 100);
        eroforras.setElet(90);
        eroforras.eletVisszatoltes();

        assertEquals(95, eroforras.getElet());
    }

    @Test
    void testEletVisszatoltMaxFelett() {
        Eroforras eroforras = new Eroforras(100, 100, 100);
        eroforras.setElet(98);
        eroforras.eletVisszatoltes();

        assertEquals(100, eroforras.getElet());
    }


    @Test
    void testRosszHalal() {
        Eroforras eroforras = new Eroforras(100, 100, 100);
        assertFalse(eroforras.halott());
    }

    @Test
    void testHalal() {
        Eroforras eroforrasElet = new Eroforras(0, 100, 100);
        Eroforras eroforrasItal = new Eroforras(100, 0, 100);
        Eroforras eroforrasEtel = new Eroforras(100, 100, 0);

        assertTrue(eroforrasElet.halott());
        assertTrue(eroforrasItal.halott());
        assertTrue(eroforrasEtel.halott());
    }
}