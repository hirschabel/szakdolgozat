package hu.szakdolgozat.megjelenites.kepernyo;

import hu.szakdolgozat.megjelenites.Ablak;
import hu.szakdolgozat.util.HashUtil;
import hu.szakdolgozat.kommunikacio.SzerverKapcsolat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BejelentkezoKepernyo extends Kepernyo implements ActionListener {
    private static final String IP_ADDR = "127.0.0.1";
    private static final int PORT = 52564;
    private final Ablak ablak;
    private JButton csatlakozas;
    private JTextField felhasznalonevInput;
    private JPasswordField jelszoInput;
    private JLabel felhasznalonevLabel;
    private JLabel jelszoLabel;

    public BejelentkezoKepernyo(int szelesseg, int magassag, Ablak ablak) {
        super(szelesseg, magassag);
        this.ablak = ablak;
        komponensekLetrehozasa();
        init(csatlakozas, felhasznalonevInput, felhasznalonevLabel, jelszoInput, jelszoLabel);
    }

    private void komponensekLetrehozasa() {
        csatlakozas = new JButton("Bejelentkezés");
        csatlakozas.setBounds(ABLAK_SZELESSEG / 2 - 75, ABLAK_MAGASSAG / 2 - 15, 150, 30);
        csatlakozas.addActionListener(this);
        csatlakozas.setActionCommand("login");

        felhasznalonevLabel = new JLabel("Felhasználónév:");
        felhasznalonevLabel.setBounds(ABLAK_SZELESSEG / 2 - 100, ABLAK_MAGASSAG / 2 - 150 - 15, 200, 30);

        felhasznalonevInput = new JTextField();
        felhasznalonevInput.setBounds(ABLAK_SZELESSEG / 2 - 100, ABLAK_MAGASSAG / 2 - 125 - 15, 200, 30);

        jelszoLabel = new JLabel("Jelszó:");
        jelszoLabel.setBounds(ABLAK_SZELESSEG / 2 - 100, ABLAK_MAGASSAG / 2 - 85 - 15, 200, 30);

        jelszoInput = new JPasswordField();
        jelszoInput.setBounds(ABLAK_SZELESSEG / 2 - 100, ABLAK_MAGASSAG / 2 - 60 - 15, 200, 30);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("login".equals(e.getActionCommand())) {
            SzerverKapcsolat kapcsolat = new SzerverKapcsolat();
            String felhasznalonev = felhasznalonevInput.getText();
            String jelszo = new String(jelszoInput.getPassword());

            jelszo = HashUtil.encrypt(jelszo);

            if (jelszo != null && felhasznalonev.length() > 0 && jelszo.length() > 0 && kapcsolat.csatlakozas(IP_ADDR, PORT, felhasznalonev, jelszo)) {
                System.out.println("Sikeres bejelentkezés");
                kapcsolat.inputHallgatas();
                ablak.jatekmenetMegjelenites(kapcsolat);
            } else {
                JOptionPane.showMessageDialog(this, "Hibás bejelentkezési adatok!");
                System.out.println("Sikertelen bejelentkezés!") ;
            }
        }
    }
}
