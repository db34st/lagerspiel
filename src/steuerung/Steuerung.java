package steuerung;
import regal.Regalfach;

import auftraege.*;
import gui.Start;
import produkte.Produkt;
public class Steuerung {
    Regalfach dasRegal[] = new Regalfach[9];
    Start dieGui;
    CsvReader derScanner;
    Auftragsliste dieAuftragsListe;
    
    Auftrag aFokusAuftrag;
    Regalfach aFokusRegalFach;
    
    
    public Steuerung(Start dieGui){
    	this.dieGui = dieGui;
        derScanner = new CsvReader();     
    	init();
    }
    private void init(){
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
    private void einlagern(Auftrag pAuftrag, Regalfach pRegalFach){
    	if(pAuftrag.getProdukt().pruefeObEinlagerbar(pRegalFach.getPosX(), pRegalFach.getPosY(), 3)) {
    		try {
    			dieAuftragsListe.getAuftragById(pAuftrag.getId());	//Test ob Auftrag vorhanden
    			pRegalFach.setProdukt(pAuftrag.getProdukt());
    			// neuerBilanzEintrag()
    			
    			dieAuftragsListe.entferneAuftragById(pAuftrag.getId());
    			dieGui.aktualisiereRegal(dasRegal);
    			dieGui.aktualisiereAuftragsListe(dieAuftragsListe);
    			aFokusAuftrag = null;
    			aFokusRegalFach = null;
    		}
    		catch (Exception e) {
    			System.out.println("Fehler beim einlagern: "+e.getMessage());
    		}    		
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
    		pRegalFach.setProdukt(null);
    		
    		dieAuftragsListe.entferneAuftragById(pAuftrag.getId());
			dieGui.aktualisiereRegal(dasRegal);
			dieGui.aktualisiereAuftragsListe(dieAuftragsListe);
			aFokusAuftrag = null;
			aFokusRegalFach = null;
    		}
    		catch (Exception e) {
    			System.out.println("Fehler beim einlagern: "+e.getMessage());
    		}
    	}
    }
    public void fokusiereAuftrag(int pIndex) {
    	try {
    		aFokusAuftrag = dieAuftragsListe.getAuftrag(pIndex);
    	}
    	catch (Exception e) {
    		System.out.println(e.getMessage());
    	}
    }
    public void resetFokusAuftrag() {
    	aFokusAuftrag = null;
    }
    public void fokusiereRegalFach(int pRegalFach) {
    	aFokusRegalFach = dasRegal[pRegalFach];
    	System.out.println("Fokus auf Fach: x=" + aFokusRegalFach.getPosX() + " y="+aFokusRegalFach.getPosY());
    	
    	if(aFokusAuftrag != null) {
    		if(aFokusAuftrag.getAuftragsArt().equals("Einlagerung"))
    			einlagern(aFokusAuftrag, aFokusRegalFach);
    		else if (aFokusAuftrag.getAuftragsArt().equals("Auslagerung"))
    			auslagern(aFokusAuftrag, aFokusRegalFach);
    	}
    }
    public void resetFokusRegalFach() {
    	aFokusRegalFach = null;
    }
    public void resetAuftragsListe() {
    	dieAuftragsListe = null;
    }
}