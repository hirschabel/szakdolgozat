package hu.szakdolgozat.hajo;

import hu.szakdolgozat.jatekos.Eroforras;
import hu.szakdolgozat.jatekos.Eszkoztar;
import hu.szakdolgozat.jatekos.Jatekos;
import hu.szakdolgozat.Pozicio;
import hu.szakdolgozat.dao.SzintAdatDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class HajoTest {

    private SzintAdatDao szintAdatDao;
    private Eszkoztar eszkoztar;
    private Jatekos jatekos;
    private Pozicio pozicio;

    @BeforeEach
    void setUp() {
        szintAdatDao = Mockito.mock(SzintAdatDao.class);
        eszkoztar = Mockito.mock(Eszkoztar.class);
        jatekos = Mockito.mock(Jatekos.class);
        pozicio = new Pozicio(0, 0);
    }

    @Test
    void testSzintlepes() {
        Mockito.when(szintAdatDao.getSzintAdat(Mockito.any(Integer.class))).thenReturn(new SzintAdat(1, new int[]{0, 0, 0}, 0, 0, false, false, null));
        Mockito.when(eszkoztar.targyCsokkentes(Mockito.any())).thenReturn(true);
        Mockito.when(jatekos.getEroforrasok()).thenReturn(Mockito.mock(Eroforras.class));
        Hajo hajo = new Hajo(pozicio, 1, szintAdatDao);
        hajo.jatekos = jatekos;
        hajo.szintlepes(eszkoztar);

        assertEquals(2, hajo.getSzint());
    }

    @Test
    void testHalott() {
        Mockito.when(szintAdatDao.getSzintAdat(Mockito.anyInt())).thenReturn(new SzintAdat(1, null, 0, 0, false, false, null));
        Mockito.when(jatekos.getEroforrasok()).thenReturn(Mockito.mock(Eroforras.class));
        Hajo hajo = new Hajo(pozicio, 1, szintAdatDao);
        hajo.jatekos = jatekos;
        hajo.halott();

        assertEquals(0, hajo.getSzint());
    }

    @Test
    void testVizVisszatoltesNincs() {
        Mockito.when(szintAdatDao.getSzintAdat(Mockito.anyInt())).thenReturn(new SzintAdat(1, null, 0, 0, false, false, null));
        Hajo hajo = new Hajo(pozicio, 1, szintAdatDao);
        assertFalse(hajo.viztisztito());
    }

    @Test
    void testVizVisszatoltesVan() {
        Mockito.when(szintAdatDao.getSzintAdat(Mockito.anyInt())).thenReturn(new SzintAdat(1, null, 0, 0, true, false, null));
        Hajo hajo = new Hajo(pozicio, 1, szintAdatDao);
        assertTrue(hajo.viztisztito());
    }

    @Test
    void testEtelVisszatoltesNincs() {
        Mockito.when(szintAdatDao.getSzintAdat(Mockito.anyInt())).thenReturn(new SzintAdat(1, null, 0, 0, false, false, null));
        Hajo hajo = new Hajo(pozicio, 1, szintAdatDao);
        assertFalse(hajo.tuzhely());
    }

    @Test
    void testEtelVisszatoltesVan() {
        Mockito.when(szintAdatDao.getSzintAdat(Mockito.anyInt())).thenReturn(new SzintAdat(1, null, 0, 0, false, true, null));
        Hajo hajo = new Hajo(pozicio, 1, szintAdatDao);
        assertTrue(hajo.tuzhely());
    }
}