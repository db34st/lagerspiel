package bilanz;
import auftraege.Auftrag;
public class Bilanzeintrag { // Matrikel-Nr: 2832690
	private Auftrag aAuftrag;
	private boolean aAusgefuehrt;
	public Bilanzeintrag next;
	
	public Bilanzeintrag(Auftrag aAuftrag, boolean aAusgefuehrt) {
		this.aAuftrag = aAuftrag;
		this.aAusgefuehrt = aAusgefuehrt;
	}
	public Auftrag getAuftrag() {
		return aAuftrag;
	}
	public boolean getAusgefuehrt() {
		return aAusgefuehrt;
	}
}