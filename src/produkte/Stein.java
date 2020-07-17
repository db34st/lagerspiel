package produkte;

import enums.ursache;
import exceptions.EinlagerException;

public class Stein extends Produkt{
    public Stein(String aAttribut1, String aAttribut2){
        super(aAttribut1, aAttribut2);
        aProduktName = "Stein";
    }
    public void pruefeObEinlagerbar(int posX, int posY, int regalTiefe) throws EinlagerException{
    	if(regalTiefe <= 0)
    		throw new EinlagerException(ursache.schonVoll);
    	
        if(getAttribut2().equals("Schwer") && posY != 2)
        	throw new EinlagerException(ursache.schwererSteinMussUnten);
    }
}