package produkte;
public class Holz extends Produkt{
    public Holz(String aAttribut1, String aAttribut2){
        super(aAttribut1, aAttribut2);
        aProduktName = "Holz";
    }
    public boolean pruefeObEinlagerbar(int posX, int posY, int regalTiefe){
        return getAttribut2() != "Balken" || regalTiefe == 3;
    }
}