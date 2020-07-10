public class Papier extends Produkt{
    public Papier(int pErstesAttribut, int pZweitesAttribut){
        super(pErstesAttribut, pZweitesAttribut);
    }
    public Boolean pruefeObEinlagerbar(){
        return true;
    }
}