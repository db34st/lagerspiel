package produkte;
public abstract class Produkt {
    private String aAttribut1, aAttribut2;
    protected String aProduktName;
    public Produkt(String aAttribut1, String aAttribut2){
        this.aAttribut1 = aAttribut1;
        this.aAttribut2 = aAttribut2;
    }
    public String getAttribut1(){
        return aAttribut1;
    }
    public String getAttribut2(){
        return aAttribut2;
    }
    public String getProduktName() {
    	return aProduktName;
    }
    public abstract boolean pruefeObEinlagerbar(int posX, int posY, int regalTiefe);
}
