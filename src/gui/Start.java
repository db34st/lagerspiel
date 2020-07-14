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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

import auftraege.*;

import javax.swing.border.EmptyBorder;
import java.awt.Component;
import javax.swing.UIManager;

public class Start {

	private JFrame frame;
	private Steuerung dieSteuerung;
	JButton btnNeuerAuftrag;
	
	int maxAnzahlAuftraege = 3;
	JPanel[] pnlAuftrag = new JPanel[maxAnzahlAuftraege]; 
	JLabel[] lblAuftragsArt = new JLabel[maxAnzahlAuftraege];
	JLabel[] lblProduktName = new JLabel[maxAnzahlAuftraege];
	JLabel[] lblProduktAttr1 = new JLabel[maxAnzahlAuftraege];
	JLabel[] lblProduktAttr2 = new JLabel[maxAnzahlAuftraege];
	JLabel[] lblBelohnung = new JLabel[maxAnzahlAuftraege];
	
	JButton[] btnRegalFach = new JButton[9];
	
	
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
	public void aktualisiereAuftragsListe(Auftragsliste pListe) {
		Auftrag[] auftraege = new Auftrag[3];
		int i = 0;
		do{
			auftraege[i] = pListe.elem();
			lblProduktName[i].setText(auftraege[i].getProdukt().getProduktName());
			lblProduktAttr1[i].setText(auftraege[i].getProdukt().getAttribut1());
			lblProduktAttr2[i].setText(auftraege[i].getProdukt().getAttribut2());
			lblBelohnung[i].setText(auftraege[i].getBelohnung() + "€");
			pListe.advance();
			i++;
			if(i == 3) btnNeuerAuftrag.setEnabled(false);
		}while(!pListe.endpos());		
	}
	
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 719, 910);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel pnlTop = new JPanel();
		pnlTop.setBackground(Color.WHITE);
		frame.getContentPane().add(pnlTop, BorderLayout.NORTH);
		
		JLabel lblNewLabel_2 = new JLabel("Lagerspiel");
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 50));
		pnlTop.add(lblNewLabel_2);
		
		JPanel pnlCenter = new JPanel();
		pnlCenter.setOpaque(false);
		pnlCenter.setSize(new Dimension(300, 300));
		pnlCenter.setMinimumSize(new Dimension(300, 300));
		pnlCenter.setMaximumSize(new Dimension(300, 300));
		pnlCenter.setPreferredSize(new Dimension(300, 300));
		frame.getContentPane().add(pnlCenter, BorderLayout.CENTER);
		

		for(int n = 0; n < 9; n++) {
			btnRegalFach[n] = new JButton("Lagerplatz");
			btnRegalFach[n].setPreferredSize(new Dimension(150, 150));
			pnlCenter.add(btnRegalFach[n]);
		}
		
		JPanel pnlButtons = new JPanel();
		pnlCenter.add(pnlButtons);
		
		btnNeuerAuftrag = new JButton("Neuer Auftrag");
		btnNeuerAuftrag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Klick neuer Auftrag");
				Auftragsliste liste = dieSteuerung.neuerAuftrag();
				aktualisiereAuftragsListe(liste);
			}
		});
		pnlButtons.add(btnNeuerAuftrag);
		
		JButton btnSchrott = new JButton("Verschrotten");
		pnlButtons.add(btnSchrott);
		
		JButton btnUmlagern = new JButton("Umlagern");
		pnlButtons.add(btnUmlagern);
		
		JPanel pnlAuftraege = new JPanel();
		pnlAuftraege.setBorder(UIManager.getBorder("DesktopIcon.border"));
		pnlCenter.add(pnlAuftraege);
		pnlAuftraege.setFont(new Font("Tahoma", Font.PLAIN, 36));
		
		
		JPanel pnl1 = new JPanel();
		pnl1.setPreferredSize(new Dimension(500, 200));
		pnl1.setMaximumSize(new Dimension(500, 32767));
		pnl1.setBorder(new LineBorder(new Color(0, 0, 0), 4));
		pnlAuftraege.add(pnl1);
		
		for(int n = 0; n< maxAnzahlAuftraege; n++) {
			pnlAuftrag[n] = new JPanel();
			pnlAuftrag[n].setPreferredSize(new Dimension(400, 50));
			pnlAuftrag[n].setMaximumSize(new Dimension(400, 32767));		       
			pnl1.add(pnlAuftrag[n]);			
		}

		for(int n = 0; n < 3; n++) {
			lblProduktName[n] = new JLabel("Produktname");
			lblProduktName[n].setFont(new Font("Dialog", Font.PLAIN, 24));
			lblProduktName[n].setBorder(null);
			pnlAuftrag[n].add(lblProduktName[n]);
			
			lblProduktAttr1[n] = new JLabel("Attr1");
			lblProduktAttr1[n].setFont(new Font("Tahoma", Font.PLAIN, 24));
			pnlAuftrag[n].add(lblProduktAttr1[n]);
			
			lblProduktAttr2[n] = new JLabel("Attr2");
			lblProduktAttr2[n].setFont(new Font("Tahoma", Font.PLAIN, 24));
			pnlAuftrag[n].add(lblProduktAttr2[n]);
		
			lblBelohnung[n] = new JLabel("Belohnung");
			lblBelohnung[n].setFont(new Font("Tahoma", Font.PLAIN, 24));
			pnlAuftrag[n].add(lblBelohnung[n]);
		}
		
		
		JPanel pnlBottom = new JPanel();
		pnlBottom.setAutoscrolls(true);
		pnlBottom.setBorder(null);
		pnlBottom.setSize(new Dimension(50, 50));
		pnlBottom.setMinimumSize(new Dimension(50, 50));
		pnlBottom.setPreferredSize(new Dimension(50, 50));
		frame.getContentPane().add(pnlBottom, BorderLayout.SOUTH);
		
		JPanel pnlLeft = new JPanel();
		frame.getContentPane().add(pnlLeft, BorderLayout.WEST);
		pnlLeft.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JScrollPane pnlBilanz = new JScrollPane();
		pnlBilanz.setToolTipText("");
		pnlLeft.add(pnlBilanz);
		
		JLabel lblNewLabel = new JLabel("Bilanz");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 36));
		pnlLeft.add(lblNewLabel);
	}

}
