package auftraege;
import produkte.*;
public class Auftrag {
	static int idCounter;
    private int aId, aBelohnung;
    private boolean aAuftragsArt; //true=einlagern, false=auslagern
    private Produkt aProdukt;
    
    public Auftrag(String pProdukt, String pAttr1, String pAttr2, int pBelohnung) throws Exception{
    	aId = idCounter++;
    	aBelohnung = pBelohnung;
    	switch(pProdukt) {
    	case "Papier": 
    		aProdukt = new Papier(pAttr1, pAttr2);
    		break;
    	case "Holz": 
    		aProdukt = new Holz(pAttr1, pAttr2);
    		break;
    	case "Stein": 
    		aProdukt = new Stein(pAttr1, pAttr2);
    		break;
    		default:
    			throw new Exception("eingelesenes Produkt nicht vorhanden!");
    	}
    }
}