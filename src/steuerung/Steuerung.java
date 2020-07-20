package steuerung;
import regal.*;
import auftraege.*;
import bilanz.*;
import enums.AuftragsArt;
import exceptions.*;
import gui.*;
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
    		}
    		catch (AuftragsException e) {
    			System.out.println(e.getMessage());
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
			
			dieGui.aktualisiereGui();
		}
		catch (AuftragsException | RegalException e) {
			System.out.println(e.getMessage());
		}
    	
    	modus = mode.leerlauf;
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
    		}
    		catch (AuftragsException | RegalException e) {
    			System.out.println(e.getMessage());
    		}
    	}
    	modus = mode.leerlauf;
    }
    public void verschrotten() {
    	try {
	    	if(aFokusRegalFach != null) {
	    		aFokusRegalFach.popProdukt();
	    		dieBilanz.neuerEintrag(new Bilanzeintrag(new Auftrag(AuftragsArt.verschrotten, 500),false));
	    		dieGui.aktualisiereGui();	    		
	    		dieGui.setBtnNeuerAuftragEnabled(true);
	    		dieGui.setBtnUmlagernEnabled(false);
	    		dieGui.setBtnSchrottEnabled(false);
	    	}
    	}
    	catch (RegalException e) {
    		System.out.println(e.getMessage());
    	}
    	modus = mode.leerlauf;
    }
    public void umlagern(Regalfach ziel) throws Exception {
    	if(modus == mode.leerlauf && aFokusRegalFach != null) {
    		modus = mode.umlagern;
    		dieGui.setBtnSchrottEnabled(false);
    		for(int i = 0; i < 9; i++) {
				try {
					//probiert alle Regale durch, nur die, die m�glich sind, werden auf eneabled geschaltet
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
	    		}
	    		catch (RegalException e) {
	    			System.out.println(e.getMessage());
	    		}
    		}
    		else {
    			dieGui.setBtnNeuerAuftragEnabled(true);
    			dieGui.setBtnAbbruchAuftragEnabled(false);
    			dieGui.setBtnSchrottEnabled(false);
    			dieGui.setBtnUmlagernEnabled(false);
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
		dieGui.setBtnNeuerAuftragEnabled(false);
		dieGui.setBtnSchrottEnabled(false);
		dieGui.setBtnUmlagernEnabled(false);
		dieGui.setBtnAbbruchAuftragEnabled(true);
		for(int i = 0; i < 9; i++) {
			if(modus == mode.einlagern) {
				try {
					//probiert alle Regale durch, nur die, die m�glich sind, werden auf eneabled geschaltet
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
    		dieGui.setBtnNeuerAuftragEnabled(true);
    		dieGui.setBtnAbbruchAuftragEnabled(false);
    		for(int i = 0; i < 9; i++)
        		if(dasRegal[i].getProdukt() == null)
        			dieGui.setBtnRegalFachEnabled(false, i);
        		else
        			dieGui.setBtnRegalFachEnabled(true, i);
    		break;
    	case auslagern:
    		auslagern(aFokusAuftrag, dasRegal[pRegalFach]);
    		dieGui.setBtnNeuerAuftragEnabled(true);
    		dieGui.setBtnRegalFachEnabled(false);
    		break;
    	case leerlauf:
    		if(aFokusRegalFach == null) {
	    		aFokusRegalFach = dasRegal[pRegalFach];
	    		dieGui.setBtnNeuerAuftragEnabled(false);
	    		dieGui.setBtnAbbruchAuftragEnabled(false);
	    		dieGui.setBtnSchrottEnabled(true);
				dieGui.setBtnUmlagernEnabled(true);
	    		System.out.println("leerlauf");
    		}
    		else {
    			resetFokusRegalFach();
    			dieGui.setBtnNeuerAuftragEnabled(true);
	    		dieGui.setBtnAbbruchAuftragEnabled(false);
	    		dieGui.setBtnSchrottEnabled(false);
				dieGui.setBtnUmlagernEnabled(false);
    		}
    		break;
    	case umlagern:
    		try {
    			umlagern(dasRegal[pRegalFach]);
    			dieGui.setBtnNeuerAuftragEnabled(true);
    			dieGui.setBtnSchrottEnabled(false);
    			dieGui.setBtnUmlagernEnabled(false);
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
    	dieGui.setBtnAbbruchAuftragEnabled(false);
    	dieGui.setBtnNeuerAuftragEnabled(true);
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
}
enum mode{
	leerlauf,
	einlagern,
	auslagern,
	umlagern
}