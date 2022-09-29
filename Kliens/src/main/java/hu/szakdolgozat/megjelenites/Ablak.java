package hu.szakdolgozat.megjelenites;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ablak extends JFrame implements ActionListener {
    private final int ABLAK_SZELESSEG = 800;
    private final int ABLAK_MAGASSAG = 600;

    private JButton connectButton;
    private JButton connectButton2;

    public Ablak() {
        connectButton = new JButton("CONNECT");
        connectButton.setBounds(ABLAK_SZELESSEG / 2 - 50, ABLAK_MAGASSAG / 2 - 15, 100, 30);
        connectButton.addActionListener(this);

        connectButton2 = new JButton("CONNECT2");
        connectButton2.setBounds(ABLAK_SZELESSEG / 2 - 200, ABLAK_MAGASSAG / 2 - 60, 100, 30);
        connectButton2.addActionListener(this);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setPreferredSize(new Dimension(ABLAK_SZELESSEG, ABLAK_MAGASSAG));
        this.setLayout(null);
        this.pack();


        this.setLocationRelativeTo(null);
        this.add(connectButton);
        this.add(connectButton2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == connectButton) {
            // connect to server (with name and pass, here:admin;admin)
        } else if (e.getSource() == connectButton2) {
            // this is just for example to how to continue the if
        }
    }
}
