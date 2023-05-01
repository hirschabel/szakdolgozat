package hu.szakdolgozat;

import hu.szakdolgozat.adatok.JatekAdatLista;
import hu.szakdolgozat.dao.JatekosDao;
import hu.szakdolgozat.dao.SzintAdatDao;
import hu.szakdolgozat.hajo.Hajo;
import hu.szakdolgozat.adatok.targyak.Bot;
import hu.szakdolgozat.adatok.targyak.Level;
import hu.szakdolgozat.adatok.targyak.Targy;
import hu.szakdolgozat.adatok.targyak.Uveg;
import hu.szakdolgozat.jatekos.Eszkoztar;
import hu.szakdolgozat.jatekos.Jatekos;
import hu.szakdolgozat.logika.Jatekmenet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JatekmenetTest {
    private Jatekmenet jatekmenet;

    @BeforeEach
    void setUp() {
        jatekmenet = new Jatekmenet(new long[100][100], new ArrayList<>(), mock(JatekAdatLista.class));
        JatekosDao mockJatekosDao = Mockito.mock(JatekosDao.class);
        doNothing().when(mockJatekosDao).jatekosokMentese(any());
        doNothing().when(mockJatekosDao).jatekosMentes(any());
        jatekmenet.setJatekosDao(mockJatekosDao);
    }

    @Test
    void testTagyGeneralas() {
        int initialSize = jatekmenet.getTargyak().size();
        jatekmenet.tagyGeneralas();
        assertEquals(initialSize + 5, jatekmenet.getTargyak().size());

        for (Targy targy : jatekmenet.getTargyak()) {
            assertTrue(targy instanceof Uveg || targy instanceof Bot || targy instanceof Level);
            assertEquals(0, targy.getPozicio().getSorPozicio());
            assertTrue(0 <= targy.getPozicio().getOszlopPozicio() && targy.getPozicio().getOszlopPozicio() < 100);
        }
    }

    @Test
    void testInputKezeles() {
        Jatekos jatekos = mock(Jatekos.class);
        Hajo hajo = mock(Hajo.class);
        Pozicio jatekosPoz = new Pozicio(10, 10);
        Pozicio hajoPoz = new Pozicio(20, 20);

        when(jatekos.getPozicio()).thenReturn(jatekosPoz);
        when(jatekos.getHajo()).thenReturn(hajo);
        when(hajo.getPozicio()).thenReturn(hajoPoz);

        jatekmenet.inputKezeles("W", jatekos);
        assertEquals(9, jatekosPoz.getSorPozicio());
        assertEquals(10, jatekosPoz.getOszlopPozicio());

        jatekmenet.inputKezeles("D", jatekos);
        assertEquals(9, jatekosPoz.getSorPozicio());
        assertEquals(11, jatekosPoz.getOszlopPozicio());

        jatekmenet.inputKezeles("S", jatekos);
        assertEquals(10, jatekosPoz.getSorPozicio());
        assertEquals(11, jatekosPoz.getOszlopPozicio());

        jatekmenet.inputKezeles("A", jatekos);
        assertEquals(10, jatekosPoz.getSorPozicio());
        assertEquals(10, jatekosPoz.getOszlopPozicio());

        // Test cases for moving the ship
        jatekmenet.inputKezeles("I", jatekos);
        assertEquals(19, hajoPoz.getSorPozicio());
        assertEquals(20, hajoPoz.getOszlopPozicio());

        jatekmenet.inputKezeles("L", jatekos);
        assertEquals(19, hajoPoz.getSorPozicio());
        assertEquals(21, hajoPoz.getOszlopPozicio());

        jatekmenet.inputKezeles("K", jatekos);
        assertEquals(20, hajoPoz.getSorPozicio());
        assertEquals(21, hajoPoz.getOszlopPozicio());

        jatekmenet.inputKezeles("J", jatekos);
        assertEquals(20, hajoPoz.getSorPozicio());
        assertEquals(20, hajoPoz.getOszlopPozicio());
    }

    @Test
    void testTargyLeptetes() {
        Uveg uveg = new Uveg(new Pozicio(0, 0));
        jatekmenet.getTargyak().add(uveg);

        jatekmenet.targyLeptetes();

        assertEquals(1, uveg.getPozicio().getSorPozicio());
    }

    @Test
    void testTargyFelvetel() {
        Uveg uveg = new Uveg(new Pozicio(1, 1));
        jatekmenet.getTargyak().add(uveg);

        List<Jatekos> jatekosok = new ArrayList<>();
        Jatekos jatekos = mock(Jatekos.class);
        when(jatekos.getPozicio()).thenReturn(new Pozicio(1, 1));
        when(jatekos.getEszkoztar()).thenReturn(new Eszkoztar());
        when(jatekos.getHajo()).thenReturn(new Hajo(new Pozicio(10, 10), 0, new SzintAdatDao()));

        jatekosok.add(jatekos);

        jatekmenet.targyFelvetel(jatekosok);

        assertEquals(0, jatekmenet.getTargyak().size());
        assertEquals(1, jatekos.getEszkoztar().getUvegSzam());
    }

    @Test
    void testTargyFelvetelHajon() {
        Uveg uveg = new Uveg(new Pozicio(1, 1));
        jatekmenet.getTargyak().add(uveg);

        List<Jatekos> jatekosok = new ArrayList<>();
        Jatekos jatekos = mock(Jatekos.class);
        when(jatekos.getPozicio()).thenReturn(new Pozicio(1, 1));
        when(jatekos.getEszkoztar()).thenReturn(new Eszkoztar());
        when(jatekos.getHajo()).thenReturn(new Hajo(new Pozicio(1, 1), 0, new SzintAdatDao()));

        jatekosok.add(jatekos);

        jatekmenet.targyFelvetel(jatekosok);

        assertEquals(1, jatekmenet.getTargyak().size());
        assertEquals(0, jatekos.getEszkoztar().getUvegSzam());
    }

    @Test
    void testTerkepFrissites() {
        Uveg uveg = new Uveg(new Pozicio(1, 1));
        jatekmenet.getTargyak().add(uveg);

        List<Jatekos> jatekosok = new ArrayList<>();
        Jatekos jatekos = mock(Jatekos.class);
        when(jatekos.getPozicio()).thenReturn(new Pozicio(1, 1));
        when(jatekos.getHajo()).thenReturn(new Hajo(new Pozicio(10, 10), 0, new SzintAdatDao()));

        jatekosok.add(jatekos);

        jatekmenet.terkepFrissites(jatekosok);

        assertEquals(TerkepKodok.UVEG | TerkepKodok.MASIK_JATEKOS, jatekmenet.getTerkep()[1][1]);
        assertEquals(TerkepKodok.HAJO, jatekmenet.getTerkep()[10][10]);
    }

}
