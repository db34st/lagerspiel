package produkte;
public class Papier extends Produkt{
    public Papier(String aAttribut1, String aAttribut2){
        super(aAttribut1, aAttribut2);
        aProduktName = "Papier";
    }
    public boolean pruefeObEinlagerbar(int posX, int posY, int regalTiefe){
        return regalTiefe > 0; //Papier keine Besonderheiten
    }
}