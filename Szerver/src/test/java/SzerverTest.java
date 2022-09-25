import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SzerverTest {
    private Kliens kliens;

    @BeforeEach
    public void kliensLetrehozas() {
        kliens = new Kliens();
        kliens.csatlakozas("127.0.0.1", 52564);
    }

    @Test
    public void uzenetKuldesTeszt() {
        String resp = kliens.uzenetKuldes("Hello server!");
        assertEquals("[SERVER] Hello client!", resp);
    }

    @Test
    public void uzenetValtasTeszt() {
        for (int i = 0; i < 10; i++) {
            kliens.uzenetKuldes("Hello szerver!");
        }
    }
}
