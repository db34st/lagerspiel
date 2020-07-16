package produkte;
public class Holz extends Produkt{
    public Holz(String aAttribut1, String aAttribut2){
        super(aAttribut1, aAttribut2);
        aProduktName = "Holz";
    }
    public void pruefeObEinlagerbar(int posX, int posY, int regalTiefe) throws Exception{
        if((getAttribut2().equals("Balken") || regalTiefe <= 0) && regalTiefe != 3)
        	throw new Exception("Fehler beim einlagern: Holz");
    }
}