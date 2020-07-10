package produkte;
public class Papier extends Produkt{
    public Papier(String aAttribut1, String aAttribut2){
        super(aAttribut1, aAttribut2);
    }
    public boolean pruefeObEinlagerbar(int posX, int posY, int regalTiefe){
        return true; //Papier keine Besonderheiten
    }
}