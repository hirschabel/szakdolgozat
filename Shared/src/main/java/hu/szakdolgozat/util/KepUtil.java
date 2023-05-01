package hu.szakdolgozat.util;

import hu.szakdolgozat.TerkepKodok;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class KepUtil {
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
            TerkepKodok.TERKEP_MEZO, TERKEP_MEZO,
            TerkepKodok.MASIK_JATEKOS, MASIK_JATEKOS,
            TerkepKodok.SAJAT_JATEKOS, SAJAT_JATEKOS,
            TerkepKodok.BOT, BOT,
            TerkepKodok.LEVEL, LEVEL,
            TerkepKodok.UVEG, UVEG,
            TerkepKodok.HAJO, HAJO,
            TerkepKodok.CAPA, CAPA,
            TerkepKodok.VIZTISZTITO, VIZTISZTITO,
            TerkepKodok.TUZHELY, TUZHELY
    );

    public static Image findImage(long hex) {
        return HEX_KEP.get(hex);
    }

    public static Image terkepenKivul() {
        return TERKEPEN_KIVUL;
    }
}
