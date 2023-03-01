package hu.szakdolgozat;

import javax.swing.*;
import java.awt.*;

public class Kepek {
    public static Image terkepMezo = new ImageIcon("./Shared/src/main/resources/images/Viz.png").getImage();
    public static Image bot = new ImageIcon("./Shared/src/main/resources/images/Bot.png").getImage();
    public static Image level = new ImageIcon("./Shared/src/main/resources/images/Level.png").getImage();
    public static Image uveg = new ImageIcon("./Shared/src/main/resources/images/Uveg.png").getImage();
    public static Image hajo = new ImageIcon("./Shared/src/main/resources/images/Hajo.png").getImage();
    public static Image masikJatekos = new ImageIcon("./Shared/src/main/resources/images/MasikJatekos.png").getImage();
    public static Image sajatJatekos = new ImageIcon("./Shared/src/main/resources/images/SajatJatekos.png").getImage();
    public static Image terkepenKivul = new ImageIcon("./Shared/src/main/resources/images/TerkepenKivul.png").getImage();

    public static Image findImage(int hex) {
        return switch (hex) {
            case 0x00000001 -> terkepMezo; // térkép mező
            case 0x00001000 -> bot; // bot
            case 0x00010000 -> level; // levél
            case 0x00100000 -> uveg; // üveg
            case 0x01000000 -> hajo; // hajó
            case 0x00000010 -> masikJatekos; // másik játékos
            case 0x00000100 -> sajatJatekos; // saját tátékos
            case 0x10000000 -> terkepenKivul; // terkepen kivul
            default -> null;
        };
    }
}
