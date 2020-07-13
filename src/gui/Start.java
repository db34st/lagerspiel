package gui;
import steuerung.Steuerung;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.Panel;
import javax.swing.JRadioButton;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Insets;
import javax.swing.SwingConstants;

public class Start {

	private JFrame frame;
	private Steuerung dieSteuerung;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Start window = new Start();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public Start() {
		initialize();
		dieSteuerung = new Steuerung(this);
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 719, 606);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel pnlTop = new JPanel();
		pnlTop.setBackground(Color.WHITE);
		frame.getContentPane().add(pnlTop, BorderLayout.NORTH);
		
		JLabel lblNewLabel_2 = new JLabel("Lagerspiel");
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 50));
		pnlTop.add(lblNewLabel_2);
		
		JPanel pnlCenter = new JPanel();
		pnlCenter.setSize(new Dimension(300, 300));
		pnlCenter.setMinimumSize(new Dimension(300, 300));
		pnlCenter.setMaximumSize(new Dimension(300, 300));
		pnlCenter.setPreferredSize(new Dimension(300, 300));
		frame.getContentPane().add(pnlCenter, BorderLayout.CENTER);
		

		
		JButton btnNewButton = new JButton("Lagerplatz");
		btnNewButton.setPreferredSize(new Dimension(150, 150));
		
		JButton btnNewButton_1 = new JButton("Lagerplatz");
		btnNewButton_1.setPreferredSize(new Dimension(150, 150));
		
		JButton btnNewButton_2 = new JButton("Lagerplatz");
		btnNewButton_2.setPreferredSize(new Dimension(150, 150));
		
		JButton btnNewButton_3 = new JButton("Lagerplatz");
		btnNewButton_3.setPreferredSize(new Dimension(150, 150));
		
		JButton btnNewButton_4 = new JButton("Lagerplatz");
		btnNewButton_4.setPreferredSize(new Dimension(150, 150));
		
		JButton btnNewButton_5 = new JButton("Lagerplatz");
		btnNewButton_5.setPreferredSize(new Dimension(150, 150));
		
		JButton btnNewButton_6 = new JButton("Lagerplatz");
		btnNewButton_6.setPreferredSize(new Dimension(150, 150));
		
		JButton btnNewButton_7 = new JButton("New button");
		btnNewButton_7.setPreferredSize(new Dimension(150, 150));
		
		JButton btnNewButton_8 = new JButton("New button");
		btnNewButton_8.setPreferredSize(new Dimension(150, 150));
		
		pnlCenter.add(btnNewButton);
		pnlCenter.add(btnNewButton_1);
		pnlCenter.add(btnNewButton_2);
		pnlCenter.add(btnNewButton_3);
		pnlCenter.add(btnNewButton_4);
		pnlCenter.add(btnNewButton_5);
		pnlCenter.add(btnNewButton_6);
		pnlCenter.add(btnNewButton_7);
		pnlCenter.add(btnNewButton_8);
		
		JPanel pnlBottom = new JPanel();
		frame.getContentPane().add(pnlBottom, BorderLayout.SOUTH);
		
		JPanel pnlAuftraege = new JPanel();
		pnlBottom.add(pnlAuftraege);
		
		JLabel lblNewLabel_1 = new JLabel("Auftraege");
		pnlAuftraege.add(lblNewLabel_1);
		
		JPanel pnlButtons = new JPanel();
		pnlBottom.add(pnlButtons);
		
		JButton btnNeuerAuftrag = new JButton("Neuer Auftrag");
		pnlButtons.add(btnNeuerAuftrag);
		
		JButton btnSchrott = new JButton("Verschrotten");
		pnlButtons.add(btnSchrott);
		
		JButton btnUmlagern = new JButton("Umlagern");
		pnlButtons.add(btnUmlagern);
		
		JPanel pnlLeft = new JPanel();
		frame.getContentPane().add(pnlLeft, BorderLayout.WEST);
		pnlLeft.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JScrollPane pnlBilanz = new JScrollPane();
		pnlBilanz.setToolTipText("");
		pnlLeft.add(pnlBilanz);
		
		JLabel lblNewLabel = new JLabel("Bilanz");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 36));
		pnlLeft.add(lblNewLabel);
		
		JPanel pnlRight = new JPanel();
		frame.getContentPane().add(pnlRight, BorderLayout.EAST);
		
		JPanel panel = new JPanel();
		pnlRight.add(panel);
		
		JLabel label = new JLabel("Legende:");
		panel.add(label);
	}

}
