package auftraege;
public class Auftragsliste {
	private Auftrag anf, pos;
	int anzahl = 0;
	
	public Auftragsliste(Auftrag pAuftrag) {
		pos = anf = pAuftrag;
		anf.next = null;
		anzahl++;
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
    	if(endpos()) pos = anf;
    	else pos = pos.next;
    }
    public Auftrag elem() {
    	return pos;
    }
    public void neuerAuftrag(Auftrag pAuftrag) throws Exception {
    	if(anzahl > 3) throw new Exception("Es sind nur 3 Aufträge gleichzeitig möglich!");
    	pAuftrag.next = pos.next;
    	pos.next = pAuftrag;
    	advance();
    	anzahl++;
    }
    public void entferneAuftrag() {
    	pos.next = pos.next.next;
    	anzahl--;
    }
    public void waeleAuftrag(int pId) throws Exception{
    	pos = anf;
    	while(pId != pos.getId()) {
    		advance();
    		if(endpos()) throw new Exception("Id ist in der Liste nicht vorhanden!");
    	}
    }
}