package auftraege;
public class Auftragsliste {
	private Auftrag[] auftrag = new Auftrag[3];
	int anzahl = 0;
	
	public Auftragsliste(Auftrag pAuftrag) {
		auftrag[0] = pAuftrag;
		anzahl++;
	}
    public Auftrag getAuftragById(int pId) {
    	for(int i = 0; i<3; i++)
    		if(auftrag[i] != null && auftrag[i].getId() == pId)
    			return auftrag[i];
    	System.out.println("getAuftragById: Id nicht gefunden!");
    	return null;
    }
    public Auftrag getAuftrag(int pIndex) {
    	return auftrag[pIndex];
    }
    public int getAnzahl() {
    	return anzahl;
    }
    public void neuerAuftrag(Auftrag pAuftrag) throws Exception {
    	if(anzahl > 3) throw new Exception("Es sind nur 3 Aufträge gleichzeitig möglich!");
    	for(int i = 0; i<3;i++) {
    		if(auftrag[i] == null) {
    			auftrag[i] = pAuftrag;
    			break;
    		}
    	}
    	anzahl++;
    }
    public void entferneAuftrag(int pIndex) {
    	if(auftrag[pIndex] != null) {
    		auftrag[pIndex] = null;
    		anzahl--;
    	}
    }
    public void entferneAuftragById(int pId) throws Exception{
    	boolean gefunden = false;
    	for(int i = 0; i < 3; i++) {
    		if(auftrag[i] != null && auftrag[i].getId() == pId) {
    			gefunden = true;
    			auftrag[i] = null;
    			anzahl--;
    			break;
    		}
    	}
    	if(!gefunden) throw new Exception("Fehler bei entferneAuftragById: Id nicht gefunden!");
    }
}