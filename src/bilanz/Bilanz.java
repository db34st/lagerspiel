package bilanz;

public class Bilanz { // Matrikel-Nr: 2832690
    private Bilanzeintrag anf, pos;
    
    int kontostand = 0,
    	anzahl = 0;
    public Bilanz(Bilanzeintrag pEintrag) {
    	neuerEintrag(pEintrag);
    }
    public boolean empty() {
		return anf.next == null;
	}
	public boolean endpos() {
		return pos.next == null;
	}
	public void reset() {
		pos = anf;
	}
    public void advance() {
    	if(!endpos()) 
    		pos = pos.next;
    }
    public Bilanzeintrag elem() {
    	return pos;
    }
    public void neuerEintrag(Bilanzeintrag pEintrag) {
    	if(pEintrag.getAusgefuehrt())
    		kontostand += pEintrag.getAuftrag().getBelohnung();
    	else
    		kontostand -= pEintrag.getAuftrag().getBelohnung();
    	reset();
    	pEintrag.next = pos;
    	anf = pos = pEintrag;
    	anzahl++;
    }
    public int getAnzahl() {
    	return anzahl;
    }
    public int getKontoStand() {
    	return kontostand;
    }
    public String[] getString() {
    	String[] r = new String[anzahl + 2];
		r[0] = "Bilanz: " + kontostand + " €";
		r[1] = "-------------------------------";
		reset();
		for (int i = 0; i < anzahl; i++) {
			r[i+2] = pos.getString();
			advance();
		}
		reset();
    	return r;
    }
}