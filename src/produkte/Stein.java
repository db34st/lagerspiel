package produkte;
public class Stein extends Produkt{
    public Stein(String aAttribut1, String aAttribut2){
        super(aAttribut1, aAttribut2);
        aProduktName = "Stein";
    }
    public void pruefeObEinlagerbar(int posX, int posY, int regalTiefe) throws Exception{
        if(getAttribut2().equals("Schwer") && posY != 2 || regalTiefe <= 0)
        	throw new Exception("Fehler beim einlagern: Stein");
    }
}