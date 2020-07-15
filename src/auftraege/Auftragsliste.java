package auftraege;
public class Auftragsliste {
	private Auftrag[] auftrag = new Auftrag[3];
	int anzahl = 0;
	
	public Auftragsliste(Auftrag pAuftrag) {
		auftrag[0] = pAuftrag;
		anzahl++;
	}
    public Auftrag getAuftragId(int pId) {
    	for(int i = 0; i<3; i++)
    		if(auftrag[i].getId() == pId)
    			return auftrag[i];
    	return null;
    }
    public Auftrag getAuftragIndex(int pIndex) {
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
    public void entferneAuftrag(int pId) throws Exception{
    	int tempAnzahl = anzahl; 
    	for(int i = 0; i<3;i++) 
    		if(auftrag[i].getId() == pId) {
    			auftrag[i] = null;
    			anzahl--;
    			break;
    		}
    	if(anzahl == tempAnzahl) throw new Exception("Auftrag konnte nicht gelöscht werdne, da per Id nicht gefunden!");
    }
}