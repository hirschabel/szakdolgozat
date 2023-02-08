package hu.szakdolgozat;

import hu.szakdolgozat.szerver_kapcsolat.SzerverKapcsolat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

public class SzerverKliensTest {
    StubKliens client;
    static Thread szerverThread;

    @BeforeAll
    static void szerverElindit() {
        szerverThread = new Thread(() -> Szerver.main(null));
        szerverThread.start();
    }

    @BeforeEach
    void letrehozas() {
        client = new StubKliens();
    }

    //@AfterEach
    void lecsatlak() {
        client.lecsatlakozas();
    }

    @Test
    void csatlakozasTest() {
        Assertions.assertTrue(client.csatlakozas());
    }

    @Test
    void alaphelyzetTest() throws IOException, ClassNotFoundException {
        client.csatlakozas();
        int[][] terkep = client.getTerkep();
        Assertions.assertEquals(1, terkep[4][4]);
    }

    @ParameterizedTest
    @MethodSource("lepesParameterek")
    void lepesUjPozicioTeszt(char inputKarakter, int[] pozicio) throws IOException, ClassNotFoundException {
        StubKliens kliens = new StubKliens();
        client.csatlakozas();
        int[][] terkep = client.getTerkep();
        client.lepes(inputKarakter);
        terkep = client.getTerkep();
        client.lecsatlakozas();
        Assertions.assertEquals(1, terkep[pozicio[0]][pozicio[1]]);
    }

    @ParameterizedTest
    @MethodSource("lepesParameterek")
    void lepesRegiPozicioTeszt(char inputKarakter, int[] pozicio) throws IOException, ClassNotFoundException {
        client.csatlakozas();
        int[][] terkep = client.getTerkep();
        client.lepes(inputKarakter);
        terkep = client.getTerkep();
        client.lecsatlakozas();
        Assertions.assertEquals(0, terkep[4][4]);
    }

    private static Stream<Arguments> lepesParameterek() {
        return Stream.of(
                Arguments.of('W', new int[]{3, 4}),
                Arguments.of('A', new int[]{4, 3}),
                Arguments.of('S', new int[]{5, 4}),
                Arguments.of('D', new int[]{4, 5})
        );
    }


    private static class StubKliens {
        private final SzerverKapcsolat kapcsolat;

        public StubKliens() {
            kapcsolat = new SzerverKapcsolat();
        }

        public boolean csatlakozas() {
            return kapcsolat.csatlakozas("127.0.0.1", 52564, "admin", "admin");
        }

        public int[][] getTerkep() throws IOException, ClassNotFoundException {
            return kapcsolat.terkepOlvas();
        }

        public void lecsatlakozas() {
            kapcsolat.lecsatlakozas();
        }

        public void lepes(char karakter) {
            kapcsolat.uzenetKuld(String.valueOf(karakter));
        }
    }
}
