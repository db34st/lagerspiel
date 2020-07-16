package regal;
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
    public void pushProdukt(Produkt aProdukt){
        if(anzahl < 3) {
	    	Inhalt temp = new Inhalt();
	        temp.aProdukt = aProdukt;
	        temp.next = top;
	        top = temp;
	        anzahl++;
        }
    }
    public Produkt getProdukt() {
    	if(top != null)
    		return top.aProdukt;
    	else return null;
    }
    public void popProdukt() {
    	if(top != null) top = top.next;
    }
}