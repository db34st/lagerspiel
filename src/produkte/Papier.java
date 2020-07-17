package produkte;

import enums.Ursache;
import exceptions.RegalException;

public class Papier extends Produkt{
    public Papier(String aAttribut1, String aAttribut2){
        super(aAttribut1, aAttribut2);
        aProduktName = "Papier";
    }
    public void pruefeObEinlagerbar(int posX, int posY, int regalTiefe) throws RegalException{ 
    	if(regalTiefe <= 0)
    		throw new RegalException(Ursache.schonVoll);
    }
}