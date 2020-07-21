package auftraege;
import produkte.*;
import enums.AuftragsArt;
import enums.Ursache;
import exceptions.AuftragsException;
public class Auftrag { // Matrikel-Nr: 2832690
	static int idCounter;
    private int aId, aBelohnung;
    private AuftragsArt aArt;
    private Produkt aProdukt;
    
    public Auftrag(String[] temp) {
    	try {
			aId = idCounter++;
			aArt = temp[1].equals("Einlagerung") ? AuftragsArt.einlagern : AuftragsArt.auslagern;
			aBelohnung = Integer.parseInt(temp[5]);
	    	switch(temp[2]) {
	    	case "Papier": 
	    		switch(temp[3]) {
	    		case "Weiß":
	    		case "Blau":
	    		case "Grün":
	    			switch (temp[4]) {
	    			case "A3":
	    			case "A4":
	    			case "A5":
	    				aProdukt = new Papier(temp[3], temp[4]);
	    	    		break;
	    	    		default:
	    	    			throw new AuftragsException(Ursache.produktAttr2);
	    			}
	    			break;
	    		default:
	    			throw new AuftragsException(Ursache.produktAttr1);
	    		}
	    		break;
	    	case "Holz": 
	    		switch(temp[3]) {
	    		case "Kiefer":
	    		case "Buche":
	    		case "Eiche":
	    			switch (temp[4]) {
	    			case "Bretter":
	    			case "Balken":
	    			case "Scheit":
	    				aProdukt = new Holz(temp[3], temp[4]);
	    	    		break;
	    	    		default:
	    	    			throw new AuftragsException(Ursache.produktAttr2);
	    			}
	    			break;
	    		default:
	    			throw new AuftragsException(Ursache.produktAttr1);
	    		}
	    		break;
	    	case "Stein": 
	    		switch(temp[3]) {
	    		case "Marmor":
	    		case "Granit":
	    		case "Sandstein":
	    			switch (temp[4]) {
	    			case "Leicht":
	    			case "Mittel":
	    			case "Schwer":
	    				aProdukt = new Stein(temp[3], temp[4]);
	    	    		break;
	    	    		default:
	    	    			throw new AuftragsException(Ursache.produktAttr2);
	    			}
	    			break;
	    		default:
	    			throw new AuftragsException(Ursache.produktAttr1);
	    		}
	    		break;
	    	}    	
    	}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
    }
    public Auftrag(AuftragsArt aArt, int aBelohnung) {
    	this.aArt = aArt;
    	this.aBelohnung = aBelohnung;
    }
	public int getId() {
		return aId;
	}
	public int getBelohnung() {
		return aBelohnung;
	}
	public AuftragsArt getAuftragsArt() {
		return aArt;
	}
	public Produkt getProdukt() {
		return aProdukt;
	}
}