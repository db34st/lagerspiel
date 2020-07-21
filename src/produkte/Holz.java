package produkte;

import enums.Ursache;
import exceptions.RegalException;

public class Holz extends Produkt{ // Matrikel-Nr: 2832690
    public Holz(String aAttribut1, String aAttribut2){
        super(aAttribut1, aAttribut2);
        aProduktName = "Holz";
    }
    public void pruefeObEinlagerbar(int posX, int posY, int regalTiefe) throws RegalException{
    	if(regalTiefe <=0)
    		throw new RegalException(Ursache.schonVoll);
    	if(getAttribut2().equals("Balken") && regalTiefe != 3)
    		throw new RegalException(Ursache.holzBalkenBrauchtDrei);
    }
}