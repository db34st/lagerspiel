public class Stein extends Produkt{
    public Stein(int pErstesAttribut, int pZweitesAttribut){
        super(pErstesAttribut, pZweitesAttribut);
    }
    public Boolean pruefeObEinlagerbar(){
        return true;
    }
}