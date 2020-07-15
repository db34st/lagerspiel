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
import bilanz.*;
import regal.Regalfach;

import javax.swing.border.EmptyBorder;
import java.awt.Component;
import javax.swing.UIManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.JList;

public class Start {

	private JFrame frame;
	private Steuerung dieSteuerung;
	JButton btnNeuerAuftrag, btnSchrott;
	
	int maxAnzahlAuftraege = 3;
	JPanel[] pnlAuftrag = new JPanel[maxAnzahlAuftraege];
	JLabel[] lblAuftragsArt = new JLabel[maxAnzahlAuftraege];
	JLabel[] lblProduktName = new JLabel[maxAnzahlAuftraege];
	JLabel[] lblProduktAttr1 = new JLabel[maxAnzahlAuftraege];
	JLabel[] lblProduktAttr2 = new JLabel[maxAnzahlAuftraege];
	JLabel[] lblBelohnung = new JLabel[maxAnzahlAuftraege];
	JButton[] btnRegalFach = new JButton[9];
	JList<String> listBilanz;
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
	public void aktualisiereBilanz(Bilanz pBilanz) {
		String[] temp = new String[pBilanz.getAnzahl() + 1];
		temp[0] = "Bilanz: " + pBilanz.getKontoStand() + " �";
		pBilanz.reset();
		for (int i = 1; i < temp.length; i++) {
			Auftrag a = pBilanz.elem().getAuftrag();
			if(a.getProdukt()!=null) {
				String  name = a.getProdukt().getProduktName(),
						belohnung = Integer.toString(a.getBelohnung()),
						art = a.getAuftragsArt().equals("Einlagerung") ? "/\\" : "\\/",
						sign =  pBilanz.elem().getAusgefuehrt() ? "+" : "-";
				temp[i] = art + "   " + name + "  " + sign+belohnung + " �";
			}
			else if(a.getAuftragsArt().equals("Verschrotten")){
				String  name = "Verschrotten",
						belohnung = Integer.toString(a.getBelohnung()),
						sign =  pBilanz.elem().getAusgefuehrt() ? "+" : "-";
				temp[i] = name + "  " + sign+belohnung + " �";
			}	
			pBilanz.advance();
			
		}
		pBilanz.reset();
		listBilanz.setListData(temp);
		
	}
	public void aktualisiereAuftragsListe(Auftragsliste pListe) {
		Auftrag[] auftraege = new Auftrag[3];
		for(int i = 0; i< 3; i++){
			
			pnlAuftrag[i].setVisible(false);
			auftraege[i] = pListe.getAuftrag(i);
			if(auftraege[i] != null) {
				lblProduktName[i].setText(auftraege[i].getProdukt().getProduktName());
				lblProduktAttr1[i].setText(auftraege[i].getProdukt().getAttribut1());
				lblProduktAttr2[i].setText(auftraege[i].getProdukt().getAttribut2());
				lblBelohnung[i].setText(auftraege[i].getBelohnung() + "�");
				if(auftraege[i].getAuftragsArt().equals("Einlagerung"))
					lblAuftragsArt[i].setText("  /\\");
				else if(auftraege[i].getAuftragsArt().equals("Auslagerung"))
					lblAuftragsArt[i].setText("  \\/");
				else {
					System.out.println("Fehler bei AuftragsArt! => " + auftraege[i].getAuftragsArt());
				}
				pnlAuftrag[i].setBorder(new LineBorder(new Color(0, 0, 0), 1));
				setBackground(lblProduktAttr1[i], pnlAuftrag[i]);
				pnlAuftrag[i].setVisible(true);
			}
			if(pListe.getAnzahl() >= 3) btnNeuerAuftrag.setEnabled(false);
			else btnNeuerAuftrag.setEnabled(true);
		}
	}
	public void aktualisiereRegal(Regalfach pRegal[]) {
		for(int i = 0; i < 9; i++) {
			if(pRegal[i].getProdukt() != null) {
				btnRegalFach[i].setText(pRegal[i].getProdukt().getProduktName()+';'+pRegal[i].getProdukt().getAttribut2());
				setBackground(btnRegalFach[i], pRegal[i].getProdukt().getAttribut1());
			}
			else {
				btnRegalFach[i].setText("leerer Lagerplatz");
				btnRegalFach[i].setBackground(null);
			}
		}
	}
	private void setBackground(JLabel lbl, JPanel pnl) {
		switch(lbl.getText()) {
			case "Wei�":
				pnl.setBackground(new Color(0xffffff));
				break;
			case "Blau":
				pnl.setBackground(new Color(0xA9D0F5));
				break;
			case "Gr�n":
				pnl.setBackground(new Color(0xCEF6E3));
				break;
			case "Kiefer":
				pnl.setBackground(new Color(0x5F4C0B));
				break;
			case "Buche":
				pnl.setBackground(new Color(0xB18904));
				break;
			case "Eiche":
				pnl.setBackground(new Color(0x886A08));
				break;
				
			case "Marmor":
				pnl.setBackground(new Color(0xF2F2F2));
				break;
			case "Granit":
				pnl.setBackground(new Color(0x6E6E6E));
				break;
			case "Sandstein":
				pnl.setBackground(new Color(0xF7F8E0));
				break;
			default:
				System.out.println("Fehler bei Hintergrund");
				break;
		}
	}
	private void setBackground(JButton btn, String attr) {
		switch(attr) {
			case "Wei�":
				btn.setBackground(new Color(0xffffff));
				break;
			case "Blau":
				btn.setBackground(new Color(0xA9D0F5));
				break;
			case "Gr�n":
				btn.setBackground(new Color(0xCEF6E3));
				break;
			case "Kiefer":
				btn.setBackground(new Color(0x5F4C0B));
				break;
			case "Buche":
				btn.setBackground(new Color(0xB18904));
				break;
			case "Eiche":
				btn.setBackground(new Color(0x886A08));
				break;
				
			case "Marmor":
				btn.setBackground(new Color(0xF2F2F2));
				break;
			case "Granit":
				btn.setBackground(new Color(0x6E6E6E));
				break;
			case "Sandstein":
				btn.setBackground(new Color(0xF7F8E0));
				break;
			default:
				System.out.println("Fehler bei Hintergrund");
				break;
		}
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 750, 910);
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
			btnRegalFach[n].setText("leerer Lagerplatz");
			btnRegalFach[n].setBackground(null);
			pnlCenter.add(btnRegalFach[n]);
		}
		
		btnRegalFach[0].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {				
				dieSteuerung.fokusiereRegalFach(0);
			}
		});
		btnRegalFach[1].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {				
				dieSteuerung.fokusiereRegalFach(1);
			}
		});
		btnRegalFach[2].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {				
				dieSteuerung.fokusiereRegalFach(2);
			}
		});
		btnRegalFach[3].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {				
				dieSteuerung.fokusiereRegalFach(3);
			}
		});
		btnRegalFach[4].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {				
				dieSteuerung.fokusiereRegalFach(4);
			}
		});
		btnRegalFach[5].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {				
				dieSteuerung.fokusiereRegalFach(5);
			}
		});
		btnRegalFach[6].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {				
				dieSteuerung.fokusiereRegalFach(6);
			}
		});
		btnRegalFach[7].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {				
				dieSteuerung.fokusiereRegalFach(7);
			}
		});
		btnRegalFach[8].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {				
				dieSteuerung.fokusiereRegalFach(8);
			}
		});		
		
		JPanel pnlButtons = new JPanel();
		pnlCenter.add(pnlButtons);
		
		btnNeuerAuftrag = new JButton("Neuer Auftrag");
		btnNeuerAuftrag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dieSteuerung.resetFokusAuftrag();
				dieSteuerung.resetFokusRegalFach();
				System.out.println("Klick neuer Auftrag");
				Auftragsliste liste = dieSteuerung.neuerAuftrag();
				aktualisiereAuftragsListe(liste);
			}
		});
		pnlButtons.add(btnNeuerAuftrag);
		btnSchrott = new JButton("Verschrotten");
		btnSchrott.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dieSteuerung.verschrotten();
			}
		});
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
		pnl1.setBorder(new LineBorder(new Color(0, 0, 0), 1));
		pnlAuftraege.add(pnl1);
		
		for(int n = 0; n < maxAnzahlAuftraege; n++) {
			pnlAuftrag[n] = new JPanel();
			pnlAuftrag[n].setPreferredSize(new Dimension(400, 50));
			pnlAuftrag[n].setMaximumSize(new Dimension(400, 32767));
			pnlAuftrag[n].setBorder(new LineBorder(new Color(0, 0, 0), 1));
			pnlAuftrag[n].setVisible(false);
			pnl1.add(pnlAuftrag[n]);			
		}
		pnlAuftrag[0].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pnlAuftrag[0].setBorder(new LineBorder(new Color(0, 0, 0), 4));
				pnlAuftrag[1].setBorder(new LineBorder(new Color(0, 0, 0), 1));
				pnlAuftrag[2].setBorder(new LineBorder(new Color(0, 0, 0), 1));
				dieSteuerung.fokusiereAuftrag(0);
				System.out.println("Fokus Auftrag1");
			}
		});
		pnlAuftrag[1].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pnlAuftrag[0].setBorder(new LineBorder(new Color(0, 0, 0), 1));
				pnlAuftrag[1].setBorder(new LineBorder(new Color(0, 0, 0), 4));
				pnlAuftrag[2].setBorder(new LineBorder(new Color(0, 0, 0), 1));
				dieSteuerung.fokusiereAuftrag(1);
				System.out.println("Fokus Auftrag2");
			}
		});
		pnlAuftrag[2].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pnlAuftrag[0].setBorder(new LineBorder(new Color(0, 0, 0), 1));
				pnlAuftrag[1].setBorder(new LineBorder(new Color(0, 0, 0), 1));
				pnlAuftrag[2].setBorder(new LineBorder(new Color(0, 0, 0), 4));
				dieSteuerung.fokusiereAuftrag(2);
				System.out.println("Fokus Auftrag3");
			}
		});

		for(int n = 0; n < 3; n++) {
			Font f = new Font("Dialog", Font.PLAIN, 24);
			lblProduktName[n] = new JLabel("Produktname");
			lblProduktName[n].setFont(f);
			lblProduktName[n].setBorder(null);
			pnlAuftrag[n].add(lblProduktName[n]);
			
			lblProduktAttr1[n] = new JLabel("Attr1");
			lblProduktAttr1[n].setFont(f);
			pnlAuftrag[n].add(lblProduktAttr1[n]);
			
			lblProduktAttr2[n] = new JLabel("Attr2");
			lblProduktAttr2[n].setFont(f);
			pnlAuftrag[n].add(lblProduktAttr2[n]);
		
			lblBelohnung[n] = new JLabel("Belohnung");
			lblBelohnung[n].setFont(f);
			pnlAuftrag[n].add(lblBelohnung[n]);
			
			lblAuftragsArt[n] = new JLabel("Art");
			lblAuftragsArt[n].setFont(f);
			pnlAuftrag[n].add(lblAuftragsArt[n]);
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
		
		listBilanz = new JList<String>();
		String[] temp = new String[1];
		temp[0] = "Bilanz: 0 �";
		listBilanz.setListData(temp);
		listBilanz.setFont(new Font("Dialog", Font.PLAIN, 24));
		pnlLeft.add(listBilanz);
	}

}
