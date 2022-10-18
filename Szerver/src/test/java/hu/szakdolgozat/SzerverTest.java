package hu.szakdolgozat;

import hu.szakdolgozat.szerver_kapcsolat.SzerverKapcsolat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SzerverTest {
    private SzerverKapcsolat kliens;

    // TODO: MOCK OBJECTEK HASZNÁLATA TESZTELÉSHEZ

    @Test
    public void kliensLetrehozas() {
        kliens = new SzerverKapcsolat();
        assertTrue(kliens.csatlakozas("127.0.0.1", 52564, "admin", "admin"));
    }
}
