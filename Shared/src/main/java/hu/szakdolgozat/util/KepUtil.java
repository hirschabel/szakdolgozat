package hu.szakdolgozat.util;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class KepUtil {
    private static final Image TERKEP_MEZO = new ImageIcon("./Shared/src/main/resources/images/Viz.png").getImage();
    private static final Image BOT = new ImageIcon("./Shared/src/main/resources/images/Bot.png").getImage();
    private static final Image LEVEL = new ImageIcon("./Shared/src/main/resources/images/Level.png").getImage();
    private static final Image UVEG = new ImageIcon("./Shared/src/main/resources/images/Uveg.png").getImage();
    private static final Image HAJO = new ImageIcon("./Shared/src/main/resources/images/Hajo.png").getImage();
    private static final Image MASIK_JATEKOS = new ImageIcon("./Shared/src/main/resources/images/MasikJatekos.png").getImage();
    private static final Image SAJAT_JATEKOS = new ImageIcon("./Shared/src/main/resources/images/SajatJatekos.png").getImage();
    private static final Image TERKEPEN_KIVUL = new ImageIcon("./Shared/src/main/resources/images/TerkepenKivul.png").getImage();
    private static final Image CAPA = new ImageIcon("./Shared/src/main/resources/images/Capa.png").getImage();
    private static final Image VIZTISZTITO = new ImageIcon("./Shared/src/main/resources/images/Viztisztito.png").getImage();
    private static final Image TUZHELY = new ImageIcon("./Shared/src/main/resources/images/Tuzhely.png").getImage();

    private static final Map<Long, Image> HEX_KEP = Map.of(
            TerkepKodokUtil.TERKEP_MEZO, TERKEP_MEZO,
            TerkepKodokUtil.MASIK_JATEKOS, MASIK_JATEKOS,
            TerkepKodokUtil.SAJAT_JATEKOS, SAJAT_JATEKOS,
            TerkepKodokUtil.BOT, BOT,
            TerkepKodokUtil.LEVEL, LEVEL,
            TerkepKodokUtil.UVEG, UVEG,
            TerkepKodokUtil.HAJO, HAJO,
            TerkepKodokUtil.CAPA, CAPA,
            TerkepKodokUtil.VIZTISZTITO, VIZTISZTITO,
            TerkepKodokUtil.TUZHELY, TUZHELY
    );

    public static Image findImage(long hex) {
        return HEX_KEP.get(hex);
    }

    public static Image terkepenKivul() {
        return TERKEPEN_KIVUL;
    }
}
