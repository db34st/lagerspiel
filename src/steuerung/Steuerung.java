package steuerung;
import regal.Regalfach;
import auftraege.*;
import gui.Start;
public class Steuerung {
    Regalfach dasRegal[][] = new Regalfach[3][3];
    Start dieGui;
    CsvReader derScanner;
    Auftragsliste dieAuftragsListe;
        
    public Steuerung(Start dieGui){
    	this.dieGui = dieGui;
        derScanner = new CsvReader();     
    	init();
    }
    private void init(){
		for(int n=0;n<3;n++)
			for(int m=0;m<3;m++)
                dasRegal[n][m] = new Regalfach(n, m);
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
}