package hu.szakdolgozat.capa;

import hu.szakdolgozat.Pozicio;
import hu.szakdolgozat.util.TerkepKodokUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtkeresesTest {

    private static final long[][] MAP = new long[][]{
            {TerkepKodokUtil.TERKEP_MEZO, TerkepKodokUtil.TERKEP_MEZO, TerkepKodokUtil.TERKEP_MEZO, TerkepKodokUtil.TERKEP_MEZO, TerkepKodokUtil.TERKEP_MEZO},
            {TerkepKodokUtil.HAJO,        TerkepKodokUtil.TERKEP_MEZO, TerkepKodokUtil.TERKEP_MEZO, TerkepKodokUtil.TERKEP_MEZO, TerkepKodokUtil.TERKEP_MEZO},
            {TerkepKodokUtil.TERKEP_MEZO, TerkepKodokUtil.TERKEP_MEZO, TerkepKodokUtil.TERKEP_MEZO, TerkepKodokUtil.TERKEP_MEZO, TerkepKodokUtil.TERKEP_MEZO},
            {TerkepKodokUtil.TERKEP_MEZO, TerkepKodokUtil.TERKEP_MEZO, TerkepKodokUtil.TERKEP_MEZO, TerkepKodokUtil.TERKEP_MEZO, TerkepKodokUtil.TERKEP_MEZO},
            {TerkepKodokUtil.TERKEP_MEZO, TerkepKodokUtil.TERKEP_MEZO, TerkepKodokUtil.TERKEP_MEZO, TerkepKodokUtil.TERKEP_MEZO, TerkepKodokUtil.TERKEP_MEZO}
    };

    @Test
    void testUtkeresesEgyVonalban() {
        Utkereses utkereses = new Utkereses(MAP);

        Pozicio start = new Pozicio(0, 0);
        Pozicio end = new Pozicio(0, 4);

        Pozicio nextPosition = utkereses.utKereses(start, end);

        assertEquals(0, nextPosition.getSorPozicio());
        assertEquals(1, nextPosition.getOszlopPozicio());
    }

    @Test
    void testUtkeresesBlokkolassal() {
        Utkereses utkereses = new Utkereses(MAP);

        Pozicio start = new Pozicio(0, 0);
        Pozicio end = new Pozicio(4, 0);

        Pozicio nextPosition = utkereses.utKereses(start, end);

        assertEquals(0, nextPosition.getSorPozicio());
        assertEquals(1, nextPosition.getOszlopPozicio());
    }

    @Test
    void testRandomPozicio() {
        Utkereses utkereses = new Utkereses(MAP);

        Pozicio start = new Pozicio(2, 2);
        Pozicio end = new Pozicio(4, 4);

        Pozicio randomPosition = utkereses.randomPoz(start, end);

        assertTrue(
                (randomPosition.getSorPozicio() == 1 && randomPosition.getOszlopPozicio() == 2) ||
                        (randomPosition.getSorPozicio() == 3 && randomPosition.getOszlopPozicio() == 2) ||
                        (randomPosition.getSorPozicio() == 2 && randomPosition.getOszlopPozicio() == 1) ||
                        (randomPosition.getSorPozicio() == 2 && randomPosition.getOszlopPozicio() == 3)
        );
    }
}
