package produkte;

import enums.ursache;
import exceptions.RegalException;

public class Holz extends Produkt{
    public Holz(String aAttribut1, String aAttribut2){
        super(aAttribut1, aAttribut2);
        aProduktName = "Holz";
    }
    public void pruefeObEinlagerbar(int posX, int posY, int regalTiefe) throws RegalException{
    	if(regalTiefe <=0)
    		throw new RegalException(ursache.schonVoll);
    	if(getAttribut2().equals("Balken") && regalTiefe != 3)
    		throw new RegalException(ursache.holzBalkenBrauchtDrei);
    }
}