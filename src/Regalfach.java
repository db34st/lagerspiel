public class Regalfach {
    Produkt aInhalt, aNext;
    int aTiefe;
    public Regalfach(){
        aTiefe = 1;
    }
    public Regalfach(Produkt pInhalt, int pTiefe){
        aTiefe = pTiefe;
        aInhalt = pInhalt;
    }
    public boolean empty(){
        return aInhalt == null;
    }
    public void popProdukt(){
        if(empty()) throw new RuntimeException("Stack ist leer");
        aInhalt = aNext;
    }
    public void pushProdukt(Produkt pInhalt){
        if(aTiefe == 3) throw new RuntimeException("Regalfach ist schon voll");
        Regalfach temp = new Regalfach(pInhalt, aTiefe + 1);
    }
}