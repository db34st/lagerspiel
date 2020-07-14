package auftraege;
import produkte.*;
public class Auftrag {
	static int idCounter;
    private int aId, aBelohnung;
    private String aAuftragsArt; //true=einlagern, false=auslagern
    private Produkt aProdukt;
    public Auftrag next;
    
    public Auftrag(String[] temp) {
    	try {
			aId = idCounter++;
			aAuftragsArt = temp[1];
			aBelohnung = Integer.parseInt(temp[5]);
	    	switch(temp[2]) {
	    	case "Papier": 
	    		aProdukt = new Papier(temp[3], temp[4]);
	    		break;
	    	case "Holz": 
	    		aProdukt = new Holz(temp[3], temp[4]);
	    		break;
	    	case "Stein": 
	    		aProdukt = new Stein(temp[3], temp[4]);
	    		break;
	    		default:
	    			throw new Exception("Fehler in der .csv-Datei: Produktname");
	    	}    	
    	}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
    }
	public int getId() {
		return aId;
	}
	public int getBelohnung() {
		return aBelohnung;
	}
	public String getAuftragsArt() {
		return aAuftragsArt;
	}
	public Produkt getProdukt() {
		return aProdukt;
	}
}