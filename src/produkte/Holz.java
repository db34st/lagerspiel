package produkte;

import enums.ursache;
import exceptions.EinlagerException;

public class Holz extends Produkt{
    public Holz(String aAttribut1, String aAttribut2){
        super(aAttribut1, aAttribut2);
        aProduktName = "Holz";
    }
    public void pruefeObEinlagerbar(int posX, int posY, int regalTiefe) throws EinlagerException{
    	if(regalTiefe <=0)
    		throw new EinlagerException(ursache.schonVoll);
    	if(getAttribut2().equals("Balken") && regalTiefe != 3)
    		throw new EinlagerException(ursache.holzBalkenBrauchtDrei);
    }
}