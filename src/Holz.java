public class Holz extends Produkt{
    public Holz(int pErstesAttribut, int pZweitesAttribut){
        super(pErstesAttribut, pZweitesAttribut);
    }

    public Boolean pruefeObEinlagerbar() {
        return true;
    }
}