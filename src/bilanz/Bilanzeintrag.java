package bilanz;
import auftraege.Auftrag;
public class Bilanzeintrag {
	private Auftrag aAuftrag;
	private boolean aAusgefuehrt;
	public Bilanzeintrag next;
	
	public Bilanzeintrag(Auftrag aAuftrag, boolean aAusgefuehrt) {
		this.aAuftrag = aAuftrag;
		this.aAusgefuehrt = aAusgefuehrt;
	}
}