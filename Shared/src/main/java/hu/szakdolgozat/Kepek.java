package hu.szakdolgozat;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Kepek {
    public static final Image TERKEP_MEZO = new ImageIcon("./Shared/src/main/resources/images/Viz.png").getImage();
    public static final Image BOT = new ImageIcon("./Shared/src/main/resources/images/Bot.png").getImage();
    public static final Image LEVEL = new ImageIcon("./Shared/src/main/resources/images/Level.png").getImage();
    public static final Image UVEG = new ImageIcon("./Shared/src/main/resources/images/Uveg.png").getImage();
    public static final Image HAJO = new ImageIcon("./Shared/src/main/resources/images/Hajo.png").getImage();
    public static final Image MASIK_JATEKOS = new ImageIcon("./Shared/src/main/resources/images/MasikJatekos.png").getImage();
    public static final Image SAJAT_JATEKOS = new ImageIcon("./Shared/src/main/resources/images/SajatJatekos.png").getImage();
    public static final Image TERKEPEN_KIVUL = new ImageIcon("./Shared/src/main/resources/images/TerkepenKivul.png").getImage();
    public static final Image CAPA = new ImageIcon("./Shared/src/main/resources/images/Capa.png").getImage();
    public static final Image VIZTISZTITO = new ImageIcon("./Shared/src/main/resources/images/Viztisztito.png").getImage();
    public static final Image TUZHELY = new ImageIcon("./Shared/src/main/resources/images/Tuzhely.png").getImage();

    private static final Map<Long, Image> HEX_KEP = Map.of(
            TerkepKod.TERKEP_MEZO, TERKEP_MEZO,
            TerkepKod.MASIK_JATEKOS, MASIK_JATEKOS,
            TerkepKod.SAJAT_JATEKOS, SAJAT_JATEKOS,
            TerkepKod.BOT, BOT,
            TerkepKod.LEVEL, LEVEL,
            TerkepKod.UVEG, UVEG,
            TerkepKod.HAJO, HAJO,
            TerkepKod.CAPA, CAPA,
            TerkepKod.VIZTISZTITO, VIZTISZTITO,
            TerkepKod.TUZHELY, TUZHELY
    );

    public static Image findImage(long hex) {
        return HEX_KEP.get(hex);
    }
}
