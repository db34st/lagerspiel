package bilanz;
import auftraege.Auftrag;
import enums.AuftragsArt;
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
	public String getString() {
		String r = "";
		if(aAuftrag.getProdukt() != null) {
			String  name = aAuftrag.getProdukt().getProduktName(),
					belohnung = Integer.toString(aAuftrag.getBelohnung()),
					art = aAuftrag.getAuftragsArt() == AuftragsArt.einlagern ? "/\\" : "\\/",
					sign =  aAusgefuehrt ? "+" : "-";
			r = art + "   " + name + "  " + sign + belohnung + " €";
		}
		else {
			AuftragsArt art = aAuftrag.getAuftragsArt();
			String  name = art == AuftragsArt.umlagern ? "Umlagern" :
						   art == AuftragsArt.verschrotten ? "Verschrotten" :
						   art == AuftragsArt.abbruch ? "Strafe" : "",
					belohnung = Integer.toString(aAuftrag.getBelohnung()),
					sign = aAusgefuehrt ? "+" : "-";
			r = name + " " + sign + belohnung + " €";
		}
		return r;
	}
}