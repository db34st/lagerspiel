package bilanz;

public class Bilanz {
    private Bilanzeintrag anf, pos;
    public Bilanz(Bilanzeintrag pEintrag) {
    	pos = anf = pEintrag;
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
    public Bilanzeintrag elem() {
    	return pos;
    }
    public void neuerEintrag(Bilanzeintrag pEintrag) {
    	pEintrag.next = pos.next;
    	pos.next = pEintrag;
    	advance();
    }
    public void entferneAuftrag() {
    	pos.next = pos.next.next;
    }
}