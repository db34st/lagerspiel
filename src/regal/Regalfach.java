package regal;
import produkte.*;
public class Regalfach {
    int aPosX, aPosY;
    Produkt aProdukt;

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
    public void setProdukt(Produkt aProdukt){
        this.aProdukt = aProdukt;
    }
}