package produkte;

import enums.Ursache;
import exceptions.RegalException;

public class Stein extends Produkt{ // Matrikel-Nr: 2832690
    public Stein(String aAttribut1, String aAttribut2){
        super(aAttribut1, aAttribut2);
        aProduktName = "Stein";
    }
    public void pruefeObEinlagerbar(int posX, int posY, int regalTiefe) throws RegalException{
    	if(regalTiefe <= 0)
    		throw new RegalException(Ursache.schonVoll);
    	
        if(getAttribut2().equals("Schwer") && posY != 2)
        	throw new RegalException(Ursache.schwererSteinMussUnten);
    }
}