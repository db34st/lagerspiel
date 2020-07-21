package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;

import auftraege.Auftrag;
import auftraege.Auftragsliste;
import bilanz.Bilanz;
import enums.AuftragsArt;
import exceptions.AuftragsException;
import produkte.Produkt;
import regal.Regalfach;
import steuerung.Steuerung;

public class Start { // Matrikel-Nr: 2832690
	private JFrame frame;
	private Steuerung dieSteuerung;
	private JButton btnNeuerAuftrag, btnAbbruchAuftrag, btnSchrott, btnUmlagern;
	private JPanel pnlLeft, pnlCenter, pnlButtons, pnlAuftraege;
	private auftrag fokusAuftrag = null;
	private btnMode modus = btnMode.leerlauf;
	private Color cBlu = new Color(0xE0F8F7),
				  cGrn = new Color(0xCEF6CE),
				  cRed = new Color(0xF5A9A9);
	private int maxAnzahlAuftraege = 3;
	private JPanel[] pnlAuftrag = new JPanel[maxAnzahlAuftraege];
	private JLabel[] lblAuftragsArt = new JLabel[maxAnzahlAuftraege],
			 		 lblProduktName = new JLabel[maxAnzahlAuftraege],
			 		 lblProduktAttr1 = new JLabel[maxAnzahlAuftraege],
			 		 lblProduktAttr2 = new JLabel[maxAnzahlAuftraege],
			 		 lblBelohnung = new JLabel[maxAnzahlAuftraege];
	private JButton[] btnRegalFach = new JButton[9];
	private JList<String> listBilanz;
	
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
	public void aktualisiereGui() {
		aktualisiereBilanz(dieSteuerung.getBilanz());
		aktualisiereAuftragsListe(dieSteuerung.getAuftragsListe());
		aktualisiereRegal(dieSteuerung.getRegal());
		aktualisiereButtons();
	}
	public void aktualisiereButtons() {
		switch(modus) {
		case fokusAuftrag:
			btnNeuerAuftrag.setEnabled(false);
			btnNeuerAuftrag.setBackground(null);			
			btnAbbruchAuftrag.setEnabled(true);
			btnAbbruchAuftrag.setBackground(cRed);
			btnSchrott.setEnabled(false);
			btnSchrott.setBackground(null);
			btnUmlagern.setEnabled(false);
			btnUmlagern.setBackground(null);
			break;
		case fokusRegal:
			btnNeuerAuftrag.setEnabled(false);
			btnNeuerAuftrag.setBackground(null);
			btnAbbruchAuftrag.setEnabled(false);
			btnAbbruchAuftrag.setBackground(null);
			btnSchrott.setEnabled(true);
			btnSchrott.setBackground(cRed);
			btnUmlagern.setEnabled(true);
			btnUmlagern.setBackground(cGrn);
			break;
		case umlagern:
			btnNeuerAuftrag.setEnabled(false);
			btnNeuerAuftrag.setBackground(null);
			btnAbbruchAuftrag.setEnabled(false);
			btnAbbruchAuftrag.setBackground(null);
			btnSchrott.setEnabled(false);
			btnSchrott.setBackground(null);
			btnUmlagern.setEnabled(true);
			btnUmlagern.setBackground(cGrn);
			break;
		case leerlauf:
			if(dieSteuerung.getAuftragsListe() != null) {
				boolean b = dieSteuerung.getAuftragsListe().getAnzahl() < 3;
				btnNeuerAuftrag.setEnabled(b);
				btnNeuerAuftrag.setBackground(b ? cGrn : null);
			}
			else {
				btnNeuerAuftrag.setEnabled(true);
				btnNeuerAuftrag.setBackground(cGrn);
			}
			btnAbbruchAuftrag.setEnabled(false);
			btnAbbruchAuftrag.setBackground(null);
			btnSchrott.setEnabled(false);
			btnSchrott.setBackground(null);
			btnUmlagern.setEnabled(false);
			btnUmlagern.setBackground(null);
    		break;
		default:
			break;
		}
	}
	public void aktualisiereButtons(btnMode modus) {
		this.modus = modus;
		aktualisiereButtons();
	}
	public void setBtnRegalFachEnabled(boolean b, int i) {
		btnRegalFach[i].setEnabled(b);
		btnRegalFach[i].setBorder(new LineBorder(b ? Color.BLACK : Color.GRAY, b ? 5 : 1));
	}
	public void setBtnRegalFachEnabled(boolean b) {
		for(int i = 0; i < 9; i++)
			setBtnRegalFachEnabled(b, i);
	}
	public void setBtnNeuerAuftragEnabled(boolean b) {
		btnNeuerAuftrag.setEnabled(b);
	}
	public void setBtnAbbruchAuftragEnabled(boolean b) {
		btnAbbruchAuftrag.setEnabled(b);
	}
	public void setBtnSchrottEnabled(boolean b) {
		btnSchrott.setEnabled(b);
	}
	public void setBtnUmlagernEnabled(boolean b) {
		btnUmlagern.setEnabled(b);
	}
	private void aktualisiereBilanz(Bilanz pBilanz) {
		String[] temp = new String[pBilanz.getAnzahl() + 1];
		temp[0] = "Bilanz: " + pBilanz.getKontoStand() + " €";
		pBilanz.reset();
		for (int i = 1; i < temp.length; i++) {
			Auftrag a = pBilanz.elem().getAuftrag();
			if(a.getProdukt()!=null) {
				String  name = a.getProdukt().getProduktName(),
						belohnung = Integer.toString(a.getBelohnung()),
						art = a.getAuftragsArt() == AuftragsArt.einlagern ? "/\\" : "\\/",
						sign =  pBilanz.elem().getAusgefuehrt() ? "+" : "-";
				temp[i] = art + "   " + name + "  " + sign+belohnung + " €";
			}
			else{
				AuftragsArt art = a.getAuftragsArt();
				String  name = art == AuftragsArt.umlagern ? "Umlagern" :
							   art == AuftragsArt.verschrotten ? "Verschrotten" :
							   art == AuftragsArt.abbruch ? "Strafe" : "",
						belohnung = Integer.toString(a.getBelohnung()),
						sign =  pBilanz.elem().getAusgefuehrt() ? "+" : "-";
				temp[i] = name + "  " + sign+belohnung + " €";
			}
			pBilanz.advance();	
		}
		pBilanz.reset();
		listBilanz.setListData(temp);
		
	}
	private void aktualisiereAuftragsListe(Auftragsliste pListe){
		Auftrag[] auftraege = new Auftrag[3];
		for(int i = 0; i< 3; i++){
			pnlAuftrag[i].setVisible(false);
			auftraege[i] = pListe.getAuftrag(i);
			if(auftraege[i] != null) {
				Produkt p = auftraege[i].getProdukt();
				lblProduktName[i].setText(p.getProduktName());
				lblProduktAttr1[i].setText(p.getAttribut1());
				lblProduktAttr2[i].setText(p.getAttribut2());
				lblBelohnung[i].setText(auftraege[i].getBelohnung() + "€");
				if(auftraege[i].getAuftragsArt() == AuftragsArt.einlagern)
					lblAuftragsArt[i].setText("  /\\");
				else if(auftraege[i].getAuftragsArt() == AuftragsArt.auslagern)
					lblAuftragsArt[i].setText("  \\/");
				pnlAuftrag[i].setBorder(new LineBorder(new Color(0, 0, 0), 1));
				setBackground(lblProduktAttr1[i], pnlAuftrag[i]);
				pnlAuftrag[i].setVisible(true);
			}
			if(pListe.getAnzahl() >= 3) btnNeuerAuftrag.setEnabled(false);
			else btnNeuerAuftrag.setEnabled(true);
		}
	}
	private void aktualisiereRegal(Regalfach pRegal[]) {
		for(int i = 0; i < 9; i++) {
			if(pRegal[i].getProdukt() != null) {
				btnRegalFach[i].setText(pRegal[i].getProdukt().getProduktName()+" - "+pRegal[i].getProdukt().getAttribut2()+" [" + (3-pRegal[i].getTiefe()) +"/3]");
				setBtnRegalFachEnabled(true, i);
				setBackground(btnRegalFach[i], pRegal[i].getProdukt().getAttribut1());
			}
			else {
				btnRegalFach[i].setText("leerer Lagerplatz");
				btnRegalFach[i].setBackground(null);
				setBtnRegalFachEnabled(false, i);
			}
		}
	}
	private void setBackground(JLabel lbl, JPanel pnl) {
		switch(lbl.getText()) {
			case "Weiß":
				pnl.setBackground(new Color(0xffffff));
				break;
			case "Blau":
				pnl.setBackground(new Color(0xA9D0F5));
				break;
			case "Grün":
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
			case "Weiß":
				btn.setBackground(new Color(0xffffff));
				break;
			case "Blau":
				btn.setBackground(new Color(0xA9D0F5));
				break;
			case "Grün":
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
		frame.setBounds(100, 100, 878, 910);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel pnlTop = new JPanel();
		pnlTop.setBackground(cBlu);
		frame.getContentPane().add(pnlTop, BorderLayout.NORTH);
		
		JLabel lblTitle = new JLabel("Lagerspiel");
		lblTitle.setFont(new Font("Arial", Font.PLAIN, 50));
		pnlTop.add(lblTitle);
		
		pnlCenter = new JPanel();
		pnlCenter.setPreferredSize(new Dimension(300, 300));
		pnlCenter.setBackground(Color.WHITE);
		frame.getContentPane().add(pnlCenter, BorderLayout.CENTER);
		
		initBilanzListe();
		initBtnRegalFach();
		initPnlButtons();
		initPnlAuftraege();
	}
	private void initBilanzListe() {
		pnlLeft = new JPanel();
		pnlLeft.setBackground(cBlu);
		
		frame.getContentPane().add(pnlLeft, BorderLayout.WEST);
		pnlLeft.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pnlLeft.setPreferredSize(new Dimension(250, 150));
		listBilanz = new JList<String>();
		String[] temp = new String[1];
		temp[0] = "Bilanz: 0 €";
		listBilanz.setListData(temp);
		listBilanz.setFont(new Font("Dialog", Font.PLAIN, 24));
		pnlLeft.add(listBilanz);
	}
	private void initBtnRegalFach() {
		UIManager.put("Button.disabledText", new ColorUIResource(Color.BLACK));
		for(int n = 0; n < 9; n++) {
			btnRegalFach[n] = new JButton("Lagerplatz");
			btnRegalFach[n].setPreferredSize(new Dimension(150, 150));
			btnRegalFach[n].setText("leerer Lagerplatz");
			btnRegalFach[n].setEnabled(false);
			btnRegalFach[n].setBackground(null);
			pnlCenter.add(btnRegalFach[n]);
		}
		btnRegalFach[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dieSteuerung.fokusiereRegalFach(0);
			}
		});
		btnRegalFach[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dieSteuerung.fokusiereRegalFach(1);
			}
		});
		btnRegalFach[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dieSteuerung.fokusiereRegalFach(2);
			}
		});
		btnRegalFach[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dieSteuerung.fokusiereRegalFach(3);
			}
		});
		btnRegalFach[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dieSteuerung.fokusiereRegalFach(4);
			}
		});
		btnRegalFach[5].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dieSteuerung.fokusiereRegalFach(5);
			}
		});
		btnRegalFach[6].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dieSteuerung.fokusiereRegalFach(6);
			}
		});
		btnRegalFach[7].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dieSteuerung.fokusiereRegalFach(7);
			}
		});
		btnRegalFach[8].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dieSteuerung.fokusiereRegalFach(8);
			}
		});		
	}
	private void initPnlButtons() {
		pnlButtons = new JPanel();
		pnlButtons.setBackground(Color.WHITE);
		
		btnNeuerAuftrag = new JButton("Neuer Auftrag");
		btnNeuerAuftrag.setBackground(cGrn);
		btnNeuerAuftrag.setPreferredSize(new Dimension(140,50));
		btnNeuerAuftrag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Auftragsliste liste = dieSteuerung.neuerAuftrag();
					aktualisiereAuftragsListe(liste);
				} catch (AuftragsException f) {
					System.out.println(f.getMessage());
				}
				dieSteuerung.resetFokusAuftrag();
				dieSteuerung.resetFokusRegalFach();
			}
		});
		pnlButtons.add(btnNeuerAuftrag);
		
		btnAbbruchAuftrag = new JButton("Auftrag abbrechen");
		btnAbbruchAuftrag.setBackground(null);
		btnAbbruchAuftrag.setPreferredSize(new Dimension(140,50));
		btnAbbruchAuftrag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dieSteuerung.brichAuftragAb();
			}
		});
		btnAbbruchAuftrag.setEnabled(false);
		pnlButtons.add(btnAbbruchAuftrag);
		
		btnSchrott = new JButton("Verschrotten");
		btnSchrott.setBackground(null);
		btnSchrott.setPreferredSize(new Dimension(110,50));
		btnSchrott.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dieSteuerung.verschrotten();
			}
		});
		btnSchrott.setEnabled(false);
		pnlButtons.add(btnSchrott);
		
		btnUmlagern = new JButton("Umlagern");
		btnUmlagern.setBackground(null);
		btnUmlagern.setPreferredSize(new Dimension(110,50));
		btnUmlagern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dieSteuerung.umlagern(null);
				}
				catch (Exception exc) {
					System.out.println("Fehler: " + exc.getMessage());
				}
			}
		});
		btnUmlagern.setEnabled(false);
		pnlButtons.add(btnUmlagern);
		
		pnlCenter.add(pnlButtons);
	}
	private void initPnlAuftraege() {
		pnlAuftraege = new JPanel();
		pnlAuftraege.setBorder(UIManager.getBorder("DesktopIcon.border"));		pnlAuftraege.setBackground(new Color(0xE0F8F1));;
		pnlAuftraege.setFont(new Font("Tahoma", Font.PLAIN, 36));		pnlCenter.add(pnlAuftraege);		
		
		JPanel pnlAuftragsListe = new JPanel();
		pnlAuftragsListe.setPreferredSize(new Dimension(500, 200));
		pnlAuftragsListe.setBackground(cBlu);
		pnlAuftraege.add(pnlAuftragsListe);
		
		for(int n = 0; n < maxAnzahlAuftraege; n++) {
			pnlAuftrag[n] = new JPanel();
			pnlAuftrag[n].setPreferredSize(new Dimension(400, 50));
			pnlAuftrag[n].setBorder(new LineBorder(new Color(0, 0, 0), 1));
			pnlAuftrag[n].setVisible(false);
			pnlAuftragsListe.add(pnlAuftrag[n]);			
		}
		pnlAuftrag[0].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(fokusAuftrag != auftrag.a0) {
					pnlAuftrag[0].setBorder(new LineBorder(new Color(0, 0, 0), 4));
					pnlAuftrag[1].setBorder(new LineBorder(new Color(0, 0, 0), 1));
					pnlAuftrag[2].setBorder(new LineBorder(new Color(0, 0, 0), 1));
					fokusAuftrag = auftrag.a0;
					dieSteuerung.fokusiereAuftrag(0);
				}
				else {
					dieSteuerung.resetFokusAuftrag();
					fokusAuftrag = null;
					pnlAuftrag[0].setBorder(new LineBorder(new Color(0, 0, 0), 1));
				}
			}
		});
		pnlAuftrag[1].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(fokusAuftrag != auftrag.a1) {
					pnlAuftrag[0].setBorder(new LineBorder(new Color(0, 0, 0), 1));
					pnlAuftrag[1].setBorder(new LineBorder(new Color(0, 0, 0), 4));
					pnlAuftrag[2].setBorder(new LineBorder(new Color(0, 0, 0), 1));
					fokusAuftrag = auftrag.a1;
					dieSteuerung.fokusiereAuftrag(1);
				}
				else {
					dieSteuerung.resetFokusAuftrag();
					fokusAuftrag = null;
					pnlAuftrag[1].setBorder(new LineBorder(new Color(0, 0, 0), 1));
				}
			}
		});
		pnlAuftrag[2].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(fokusAuftrag != auftrag.a2) {
					pnlAuftrag[0].setBorder(new LineBorder(new Color(0, 0, 0), 1));
					pnlAuftrag[1].setBorder(new LineBorder(new Color(0, 0, 0), 1));
					pnlAuftrag[2].setBorder(new LineBorder(new Color(0, 0, 0), 4));
					fokusAuftrag = auftrag.a2;
					dieSteuerung.fokusiereAuftrag(2);
				}
				else {
					dieSteuerung.resetFokusAuftrag();
					fokusAuftrag = null;
					pnlAuftrag[2].setBorder(new LineBorder(new Color(0, 0, 0), 1));
				}
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
	}
	public enum btnMode{
		fokusAuftrag,
		fokusRegal,
		umlagern,
		leerlauf
	}
	private enum auftrag{
		a0,
		a1,
		a2
	}
}