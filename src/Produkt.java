public abstract class Produkt {
    private int aErstesAttribut, aZweitesAttribut;

    public Produkt(int pErstesAttribut, int pZweitesAttribut){
        aErstesAttribut = pErstesAttribut;
        aZweitesAttribut = pZweitesAttribut;
    }
    public int getErstesAttribut(){
        return aErstesAttribut;
    }
    public int getZweitesAttribut(){
        return aZweitesAttribut;
    }
    public abstract Boolean pruefeObEinlagerbar() throws Exception;
}
