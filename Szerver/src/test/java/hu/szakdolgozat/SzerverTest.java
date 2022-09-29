package hu.szakdolgozat;

import hu.szakdolgozat.szerver_kapcsolat.SzerverKapcsolat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SzerverTest {
    private SzerverKapcsolat kliens;

    @BeforeEach
    public void kliensLetrehozas() {
        kliens = new SzerverKapcsolat();
        assertTrue(kliens.csatlakozas("127.0.0.1", 52564, "admin", "admin"));
    }

    @Test
    public void folyamatosKuldesTeszt() throws IOException {
        String[] kuldendo = {
          "kuldok valamit",
          "null",
          "null",
          "itt is van valami",
          "W",
          "null"
        };

        for (String kuld : kuldendo) {
            kliens.uzenetKuldes(kuld);
        }

    }

    @Test
    public void folyamatosKuldesTeszt2() throws IOException {
        while (true) {
            kliens.uzenetKuldes("uzenet2");
        }
    }

    @Test
    public void uzenetKuldesTeszt() throws IOException {
        String resp = kliens.uzenetKuldes("Hello szerver!");
        assertEquals("Szia!", resp);
    }

    @Test
    public void uzenetValtasTeszt() throws IOException {
        for (int i = 0; i < 10; i++) {
            kliens.uzenetKuldes("Hello szerver!");
        }
    }

    @Test
    public void simaTeszt() throws IOException {
        String kuldendo = "kuldok valamit";


        kliens.uzenetKuldes(kuldendo);


    }
}
