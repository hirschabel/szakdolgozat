package hu.szakdolgozat;

import javax.swing.*;
import java.awt.*;

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

    public static Image findImage(int hex) {
        return switch (hex) {
            case TerkepKod.TERKEP_MEZO -> TERKEP_MEZO;
            case TerkepKod.MASIK_JATEKOS -> MASIK_JATEKOS;
            case TerkepKod.SAJAT_JATEKOS -> SAJAT_JATEKOS;
            case TerkepKod.BOT -> BOT;
            case TerkepKod.LEVEL -> LEVEL;
            case TerkepKod.UVEG -> UVEG;
            case TerkepKod.HAJO -> HAJO;
            case TerkepKod.CAPA -> CAPA;
            default -> null;
        };
    }
}
