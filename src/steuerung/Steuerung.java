package steuerung;
import regal.Regalfach;
import gui.Start;
public class Steuerung {
    Regalfach dasRegal[][] = new Regalfach[3][3];
    Start dieGui;
    public Steuerung(Start dieGui){
    	this.dieGui = dieGui;
        init();
    }
    private void init(){
		for(int n=0;n<3;n++)
			for(int m=0;m<3;m++)
                dasRegal[n][m] = new Regalfach(n, m);
                
	}
}