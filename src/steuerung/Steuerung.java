package steuerung;
import regal.*;

import javax.swing.JOptionPane;

import auftraege.*;
import bilanz.*;
import enums.AuftragsArt;
import enums.Ursache;
import exceptions.*;
import gui.*;
import gui.Start.btnMode;
import produkte.*;
public class Steuerung {
    Regalfach dasRegal[] = new Regalfach[9];
    Start dieGui;
    CsvReader derScanner;
    Auftragsliste dieAuftragsListe;
    Bilanz dieBilanz;
    Auftrag aFokusAuftrag;
    Regalfach aFokusRegalFach;
    mode modus;
    
    public Steuerung(Start dieGui){
    	this.dieGui = dieGui;
        derScanner = new CsvReader();
        for(int n=0;n<9;n++)
			dasRegal[n] = new Regalfach(n%3, n/3);
	}
    public Auftragsliste neuerAuftrag() throws AuftragsException{
    	String[] rAuftrag = derScanner.getNaechsteZeile();
	    	if(dieAuftragsListe == null) 
	    		dieAuftragsListe = new Auftragsliste(new Auftrag(rAuftrag));
	    	else 
	    		dieAuftragsListe.neuerAuftrag(new Auftrag(rAuftrag));
    	return dieAuftragsListe;
    }
    public void brichAuftragAb() {
    	if(aFokusAuftrag != null) {
    		try {
    			dieAuftragsListe.entferneAuftragById(aFokusAuftrag.getId());
    			Auftrag tempAuftrag = new Auftrag(AuftragsArt.abbruch, aFokusAuftrag.getBelohnung());
    			Bilanzeintrag tempBilanzEintrag = new Bilanzeintrag(tempAuftrag,false);
    			if(dieBilanz == null)
    				dieBilanz = new Bilanz(tempBilanzEintrag);
    			else 
    				dieBilanz.neuerEintrag(tempBilanzEintrag);
    			dieGui.aktualisiereGui();
    			dieGui.aktualisiereButtons(btnMode.leerlauf);
    		} catch (AuftragsException e) {
    			JOptionPane.showMessageDialog(null,e.getMessage(),"Auftragsfehler", JOptionPane.ERROR_MESSAGE);
    		}
    		
    	}
    }
    private void einlagern(Auftrag pAuftrag, Regalfach pRegalFach){
		try {
			pAuftrag.getProdukt().pruefeObEinlagerbar(pRegalFach.getPosX(), pRegalFach.getPosY(), pRegalFach.getTiefe());
			dieAuftragsListe.getAuftragById(pAuftrag.getId());	//Test ob Auftrag vorhanden
			pRegalFach.pushProdukt(pAuftrag.getProdukt());
			Bilanzeintrag tempBilanzEintrag = new Bilanzeintrag(pAuftrag, true);
			if(dieBilanz == null)
				dieBilanz = new Bilanz(tempBilanzEintrag);
			else 
				dieBilanz.neuerEintrag(tempBilanzEintrag);
			dieAuftragsListe.entferneAuftragById(pAuftrag.getId());
			
			resetFokusAuftrag();
			resetFokusRegalFach();
			modus = mode.leerlauf;
			dieGui.aktualisiereGui();
			dieGui.aktualisiereButtons(btnMode.leerlauf);
		} catch (AuftragsException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Auftragsfehler", JOptionPane.ERROR_MESSAGE);
		} catch (RegalException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Regalfehler", JOptionPane.CANCEL_OPTION);
		}
    	
    	
    }
    private void auslagern(Auftrag pAuftrag, Regalfach pRegalFach) {
    	Produkt ap = pAuftrag.getProdukt(),		//ap = AuftragsProdukt
    			rp = pRegalFach.getProdukt();	//rp = RegalProdukt
    	boolean name = ap.getProduktName() == rp.getProduktName(),
    			attr1 = ap.getAttribut1().equals(rp.getAttribut1()),
    			attr2 = ap.getAttribut2().equals(rp.getAttribut2());
    	if(name && attr1 && attr2) {
    		try {
	    		dieAuftragsListe.getAuftragById(pAuftrag.getId());
	    		pRegalFach.popProdukt();
	    		dieBilanz.neuerEintrag(new Bilanzeintrag(pAuftrag, true));
	    		dieAuftragsListe.entferneAuftragById(pAuftrag.getId());
	    		aFokusAuftrag = null;
				aFokusRegalFach = null;
	    		
				dieGui.aktualisiereGui();
				dieGui.aktualisiereButtons(btnMode.leerlauf);
				modus = mode.leerlauf;
    		} catch (AuftragsException e) {
    			JOptionPane.showMessageDialog(null,e.getMessage(),"Auftragsfehler", JOptionPane.ERROR_MESSAGE);
    		} catch (RegalException e) {
    			JOptionPane.showMessageDialog(null,e.getMessage(),"Regalfehler", JOptionPane.CANCEL_OPTION);
    		}
    	}
    	
    }
    public void verschrotten() {
    	try {
	    	if(aFokusRegalFach != null) {
	    		aFokusRegalFach.popProdukt();
	    		dieBilanz.neuerEintrag(new Bilanzeintrag(new Auftrag(AuftragsArt.verschrotten, 500),false));
	    		dieGui.aktualisiereGui();	    		
	    		dieGui.aktualisiereButtons(btnMode.leerlauf);
	    		modus = mode.leerlauf;
	    	}
		} catch (RegalException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Regalfehler", JOptionPane.CANCEL_OPTION);
		}
    	
    }
    public void umlagern(Regalfach ziel) throws Exception {
    	if(modus == mode.leerlauf && aFokusRegalFach != null) {
    		modus = mode.umlagern;
    		dieGui.setBtnSchrottEnabled(false);
    		for(int i = 0; i < 9; i++) {
				try {
					//probiert alle Regale durch, nur die, die möglich sind, werden auf eneabled geschaltet
					aFokusRegalFach.getProdukt().pruefeObEinlagerbar(dasRegal[i].getPosX(), dasRegal[i].getPosY(), dasRegal[i].getTiefe());
					dieGui.setBtnRegalFachEnabled(true, i);
				} catch(Exception e) {}
			}
    	}
    	else if(modus == mode.umlagern) {
    		if(ziel != null) {
	    		try {
	    			aFokusRegalFach.getProdukt().pruefeObEinlagerbar(ziel.getPosX(), ziel.getPosY(), ziel.getTiefe());
		    		ziel.pushProdukt(aFokusRegalFach.getProdukt());
		    		aFokusRegalFach.popProdukt();
		    		dieBilanz.neuerEintrag(new Bilanzeintrag(new Auftrag(AuftragsArt.umlagern, 100), false));
		    		dieGui.aktualisiereGui();
		    		modus = mode.leerlauf;
	    		} catch (RegalException e) {
	    			JOptionPane.showMessageDialog(null,e.getMessage(),"Regalfehler", JOptionPane.CANCEL_OPTION);
	    		}
    		}
    		else {
    			dieGui.aktualisiereButtons();
    			for(int i = 0; i < 9; i++)
            		if(dasRegal[i].getProdukt() == null)
            			dieGui.setBtnRegalFachEnabled(false, i);
            		else
            			dieGui.setBtnRegalFachEnabled(true, i);
    			resetFokusRegalFach();
    			modus = mode.leerlauf;
    		}
    	}
    	else throw new Exception("Fehler beim modus umlagern in Steuerung!");	
    }
    public void fokusiereAuftrag(int pIndex) {
		aFokusAuftrag = dieAuftragsListe.getAuftrag(pIndex);
		if(aFokusAuftrag.getAuftragsArt() == AuftragsArt.einlagern)
			modus = mode.einlagern;
		else if(aFokusAuftrag.getAuftragsArt() == AuftragsArt.auslagern)
			modus = mode.auslagern;
		dieGui.aktualisiereButtons(btnMode.fokusAuftrag);
		for(int i = 0; i < 9; i++) {
			if(modus == mode.einlagern) {
				try {
					//probiert alle Regale durch, nur die, die möglich sind, werden auf eneabled geschaltet
					aFokusAuftrag.getProdukt().pruefeObEinlagerbar(dasRegal[i].getPosX(), dasRegal[i].getPosY(), dasRegal[i].getTiefe());
					dieGui.setBtnRegalFachEnabled(true, i);
				} catch(RegalException e) {}
			}
			else if(modus == mode.auslagern) {
				try {
					dasRegal[i].pruefeObPassenderAuftrag(aFokusAuftrag);
					dieGui.setBtnRegalFachEnabled(true, i);
				} catch (AuftragsException e) { }
			}
		}
    }
    public void fokusiereRegalFach(int pRegalFach) {
    	switch (modus) {
    	case einlagern:
    		einlagern(aFokusAuftrag, dasRegal[pRegalFach]);
    		dieGui.aktualisiereButtons(btnMode.leerlauf);
    		for(int i = 0; i < 9; i++)
        		if(dasRegal[i].getProdukt() == null)
        			dieGui.setBtnRegalFachEnabled(false, i);
        		else
        			dieGui.setBtnRegalFachEnabled(true, i);
    		break;
    	case auslagern:
    		auslagern(aFokusAuftrag, dasRegal[pRegalFach]);
    		dieGui.aktualisiereButtons(btnMode.leerlauf);
    		break;
    	case leerlauf:
    		if(aFokusRegalFach == null) {
	    		aFokusRegalFach = dasRegal[pRegalFach];
	    		dieGui.aktualisiereButtons(btnMode.fokusRegal);
    		}
    		else {
    			resetFokusRegalFach();
    			dieGui.aktualisiereButtons(btnMode.leerlauf);
    		}
    		break;
    	case umlagern:
    		try {
    			umlagern(dasRegal[pRegalFach]);
    			dieGui.aktualisiereButtons(btnMode.leerlauf);
    		}
    		catch (Exception e) {
    			System.out.println("Fehler: " + e.getMessage());
    		}
    		break;
    	}
    }
    public void resetFokusAuftrag() {
    	aFokusAuftrag = null;
    	for(int i = 0; i < 9; i++)
    		if(dasRegal[i].getProdukt() == null)
    			dieGui.setBtnRegalFachEnabled(false, i);
    		else
    			dieGui.setBtnRegalFachEnabled(true, i);
    	dieGui.aktualisiereButtons(btnMode.leerlauf);
    }
    public void resetFokusRegalFach() {
    	aFokusRegalFach = null;
    }
    public void resetAuftragsListe() {
    	dieAuftragsListe = null;
    }
    public mode getModus() {
    	return modus;
    }
    public Bilanz getBilanz() {
    	return dieBilanz;
    }
    public Auftragsliste getAuftragsListe() {
    	return dieAuftragsListe;
    }
    public Regalfach[] getRegal(){
    	return dasRegal;
    }
    public enum mode{
    	leerlauf,
    	einlagern,
    	auslagern,
    	umlagern
    }
}