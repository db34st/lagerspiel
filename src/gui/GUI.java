package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.*;

public class GUI {
    JFrame gui;
    JButton btnRegalFach[][] = new JButton[3][3];

    JPanel regal, auftraege, bilanz;

    public GUI() {
        gui = new JFrame("Lagerspiel");
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setVisible(true);
        gui.setSize(1000,1000);

        regal = new JPanel();
        regal.setSize(350,350);
        regal.setBackground(Color.RED);
        regal.setVisible(true);
        gui.add(regal);

        auftraege = new JPanel();
        gui.add(auftraege);

        bilanz = new JPanel();
        gui.add(bilanz);

        for (int n = 0; n < 3; n++)
            for (int m = 0; m < 3; m++) {
                btnRegalFach[n][m] = new JButton();
                btnRegalFach[n][m].setPreferredSize(new Dimension(100, 100));
                btnRegalFach[n][m].setMargin(new Insets(5, 5, 5, 5));
                btnRegalFach[n][m].setBackground(Color.WHITE);
                btnRegalFach[n][m].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                regal.add(btnRegalFach[n][m]);
                
                btnRegalFach[n][m].setVisible(true);
                
           }
    }
    
}