import regal.Regalfach;
import gui.GUI;
public class Steuerung {
    Regalfach dasRegal[][] = new Regalfach[3][3];
    GUI dieGui;
    public Steuerung(){
        init();
    }
    private void init(){
		for(int n=0;n<3;n++)
			for(int m=0;m<3;m++)
                dasRegal[n][m] = new Regalfach(n, m);
                
        dieGui = new GUI();
	}
}