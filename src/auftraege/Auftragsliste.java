package auftraege;
public class Auftragsliste {
	private Auftrag anf, pos;
	public Auftragsliste(Auftrag pAuftrag) {
		pos = anf = pAuftrag;
		anf.next = null;
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
    public void neuerAuftrag(Auftrag pAuftrag) {
    	pAuftrag.next = pos.next;
    	pos.next = pAuftrag;
    }
    public void entferneAuftrag() {
    	pos.next = pos.next.next;
    }
    public void waeleAuftrag(int pId) throws Exception{
    	pos = anf;
    	while(pId != pos.getId()) {
    		advance();
    		if(endpos()) throw new Exception("Id ist in der Liste nicht vorhanden!");
    	}
    }
}