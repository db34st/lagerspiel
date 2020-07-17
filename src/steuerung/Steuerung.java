package steuerung;
import regal.*;
import auftraege.*;
import bilanz.*;
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
    public Auftragsliste neuerAuftrag() {
    	String[] rAuftrag = derScanner.getNaechsteZeile();
	    try {
	    	if(dieAuftragsListe == null) 
	    		dieAuftragsListe = new Auftragsliste(new Auftrag(rAuftrag));
	    	else 
	    		dieAuftragsListe.neuerAuftrag(new Auftrag(rAuftrag));
    	}
	    catch (Exception e) {
	    	System.out.println(e.getMessage());
	    }
    	return dieAuftragsListe;
    }
    public void brichAuftragAb() {
    	if(aFokusAuftrag != null) {
    		try {
    			dieAuftragsListe.entferneAuftragById(aFokusAuftrag.getId());
    			Auftrag tempAuftrag = new Auftrag("Abbruch", aFokusAuftrag.getBelohnung());
    			Bilanzeintrag tempBilanzEintrag = new Bilanzeintrag(tempAuftrag,false);
    			if(dieBilanz == null)
    				dieBilanz = new Bilanz(tempBilanzEintrag);
    			else 
    				dieBilanz.neuerEintrag(tempBilanzEintrag);
    			dieGui.aktualisiereAuftragsListe(dieAuftragsListe);
    			dieGui.aktualisiereBilanz(dieBilanz);
    		}
    		catch (Exception e) {
    			System.out.println("Fehler beim AuftragsAbbruch: "+e.getMessage());
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
			
			dieGui.aktualisiereRegal(dasRegal);
			dieGui.aktualisiereAuftragsListe(dieAuftragsListe);
			dieGui.aktualisiereBilanz(dieBilanz);
		}
		catch (Exception e) {
			System.out.println("Fehler beim einlagern: "+e.getMessage());
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
	    		
				dieGui.aktualisiereRegal(dasRegal);
				dieGui.aktualisiereAuftragsListe(dieAuftragsListe);
				dieGui.aktualisiereBilanz(dieBilanz);
    		}
    		catch (Exception e) {
    			System.out.println("Fehler beim einlagern: "+e.getMessage());
    		}
    	}
    	modus = mode.leerlauf;
    }
    public void verschrotten() {
    	try {
	    	if(aFokusRegalFach != null) {
	    		aFokusRegalFach.popProdukt();
	    		dieBilanz.neuerEintrag(new Bilanzeintrag(new Auftrag("Verschrotten", 500),false));
	    		dieGui.aktualisiereRegal(dasRegal);
	    		dieGui.aktualisiereBilanz(dieBilanz);
	    		dieGui.setBtnNeuerAuftragEnabled(true);
	    		dieGui.setBtnUmlagernEnabled(false);
	    		dieGui.setBtnSchrottEnabled(false);
	    	}
    	}
    	catch (Exception e) {
    		System.out.println("Fehler beim einlagern: "+e.getMessage());
    	}
    	modus = mode.leerlauf;
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
		    		dieBilanz.neuerEintrag(new Bilanzeintrag(new Auftrag("Umlagern", 100), false));
		    		dieGui.aktualisiereRegal(dasRegal);
		    		dieGui.aktualisiereBilanz(dieBilanz);
		    		modus = mode.leerlauf;
	    		}
	    		catch (Exception e) {
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
    	else throw new Exception("Fehler beim umlagern in Steuerung!");	
    }
    public void fokusiereAuftrag(int pIndex) {
    	try {
    		aFokusAuftrag = dieAuftragsListe.getAuftrag(pIndex);
    		if(aFokusAuftrag.getAuftragsArt().equals("Einlagerung"))
    			modus = mode.einlagern;
    		else if(aFokusAuftrag.getAuftragsArt().equals("Auslagerung"))
    			modus = mode.auslagern;
    		dieGui.setBtnNeuerAuftragEnabled(false);
			dieGui.setBtnSchrottEnabled(false);
			dieGui.setBtnUmlagernEnabled(false);
			dieGui.setBtnAbbruchAuftragEnabled(true);
			for(int i = 0; i < 9; i++) {
				if(modus == mode.einlagern) {
					try {
						//probiert alle Regale durch, nur die, die möglich sind, werden auf eneabled geschaltet
						aFokusAuftrag.getProdukt().pruefeObEinlagerbar(dasRegal[i].getPosX(), dasRegal[i].getPosY(), dasRegal[i].getTiefe());
						dieGui.setBtnRegalFachEnabled(true, i);
					} catch(Exception e) {}
				}
				else if(modus == mode.auslagern) {
					try {
						dasRegal[i].pruefeObPassenderAuftrag(aFokusAuftrag);
						dieGui.setBtnRegalFachEnabled(true, i);
					} catch (Exception e) { }
				}
			}
    	}
    	catch (Exception e) {
    		System.out.println(e.getMessage());
    	}
    }
    public void fokusiereRegalFach(int pRegalFach) {
    	
    	System.out.println("Fokus auf Fach: x=" + dasRegal[pRegalFach].getPosX() + " y="+dasRegal[pRegalFach].getPosY());
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
    		aFokusRegalFach = dasRegal[pRegalFach];
    		dieGui.setBtnNeuerAuftragEnabled(false);
    		dieGui.setBtnAbbruchAuftragEnabled(false);
    		dieGui.setBtnSchrottEnabled(true);
			dieGui.setBtnUmlagernEnabled(true);
    		System.out.println("leerlauf");
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
}
enum mode{
	leerlauf,
	einlagern,
	auslagern,
	umlagern
}