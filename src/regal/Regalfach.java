package regal;
import auftraege.Auftrag;
import produkte.*;
public class Regalfach {
    int aPosX, aPosY;
    private Inhalt top;
    int anzahl = 0;
    
    public Regalfach(int aPosX, int aPosY){
        this.aPosX = aPosX;
        this.aPosY = aPosY;
    }
    public int getPosX(){
        return aPosX;
    }
    public int getPosY(){
        return aPosY;
    }
    public int getTiefe() {
    	return 3 - anzahl;
    }
    public void pushProdukt(Produkt aProdukt) throws Exception{
        if(anzahl < 3) {
	    	Inhalt temp = new Inhalt();
	        temp.aProdukt = aProdukt;
	        temp.next = top;
	        top = temp;
	        if(aProdukt.getAttribut2().equals("Balken"))
	        	anzahl = 3;
	        else
	        	anzahl++;
        }
        else throw new Exception("Regalfach ist schon voll!");
    }
    public Produkt getProdukt() {
    	if(top != null)
    		return top.aProdukt;
    	else return null;
    }
    public void popProdukt() throws Exception {
    	if(top != null) {
    		if(top.aProdukt.getAttribut2().equals("Balken"))
    			anzahl = 0;
    		else
    			anzahl--;
    		top = top.next;
    	}
    	else throw new Exception("Regalfach leer!");
    }
    public void pruefeObPassenderAuftrag(Auftrag pAuftrag) throws Exception{
    	String tName = top.aProdukt.getProduktName(),
    		   pName = pAuftrag.getProdukt().getProduktName(),
    		   tAttr1 = top.aProdukt.getAttribut1(),
    		   pAttr1 = pAuftrag.getProdukt().getAttribut1(),
    		   tAttr2 = top.aProdukt.getAttribut2(),
    		   pAttr2 = pAuftrag.getProdukt().getAttribut2();
    	if(!tName.equals(pName) || !tAttr1.equals(pAttr1) || !tAttr2.equals(pAttr2))
    		throw new Exception();    	
    }
}