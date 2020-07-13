package produkte;
public class Stein extends Produkt{
    public Stein(String aAttribut1, String aAttribut2){
        super(aAttribut1, aAttribut2);
    }
    public boolean pruefeObEinlagerbar(int posX, int posY, int regalTiefe){
        return (getAttribut2()!="Schwer" || posY == 2) && regalTiefe > 0;
    }
}