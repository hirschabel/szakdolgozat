package hu.szakdolgozat;

import hu.szakdolgozat.szerver_kapcsolat.SzerverKapcsolat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TerkepTest {

    /*
    @Mock
    List list;

    @BeforeEach
    void init() {
        Mockito.lenient().when(list.get(0)).thenReturn("hehe");

    }

    @Test
    void mockitoTest() {
        Assertions.assertEquals("hehe", list.get(0));
    }

     */

    @Test
    void szerverTest() {
        try {
            ServerSocket szerver = new ServerSocket(52564);
            SzerverKapcsolat kapcs = new SzerverKapcsolat();
            new Thread(() -> {
                kapcs.csatlakozas("127.0.0.1", 52564, "admin", "admin");
            }).start();
            Socket kliensSocket = szerver.accept();
            KliensKapcsolat kapcs2 = new KliensKapcsolat(kliensSocket);
            kapcs2.kuldes(new int[][]{
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
