package hu.szakdolgozat;

import hu.szakdolgozat.megjelenites.Ablak;
import hu.szakdolgozat.szerver_kapcsolat.SzerverKapcsolat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class SzerverTest {
    private SzerverKapcsolat kliens;


    Ablak ablak;

    // TODO: MOCK OBJECTEK HASZNÁLATA TESZTELÉSHEZ



    public void letrehozas() {
        kliens = new SzerverKapcsolat();
        kliens.csatlakozas("127.0.0.1", 52564, "admin", "admin");


    }

    @BeforeEach
    public void kliensMegjelenites() {
        //szerver = mock(Szerver.class);
        Indito.main(new String[0]);
        //szerverMock.setup();
    }

    @Test
    public void kliensLetrehozas() throws AWTException, IOException {
        Robot bot = new Robot();
        bejelentkezes(bot);
        System.in.read();
    }

    private void bejelentkezes(Robot bot) {
        bot.mouseMove(1920/2, 1080/2);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.delay(20);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }


}
