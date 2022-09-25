import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SzerverTest {
    private Kliens kliens;

    @BeforeEach
    public void kliensLetrehozas() {
        kliens = new Kliens();
        assertTrue(kliens.csatlakozas("127.0.0.1", 52564, "admin", "admin"));
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
}
