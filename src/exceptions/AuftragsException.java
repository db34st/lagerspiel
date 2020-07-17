package exceptions;

import enums.Ursache;

public class AuftragsException extends Exception{
	String errMessage = "Auftragsfehler: ";
	public AuftragsException(Ursache u) {
		super();
		switch (u) {
		case idNichtGefunden:
		errMessage += "Auftrags-ID wurde nicht gefunden!";
			break;
		case maxDreiAuftraege:
			errMessage += "Es können nur drei Aufträge gleichzeitig laufen!";
			break;
		case nichtPassenderAuftrag:
			errMessage += "Auftrag passt nicht zum Produkt!";
			break;
		case produktName:
			errMessage += "Fehler beim Parsen des Produktnamens!";
			break;
		case produktAttr1:
			errMessage += "Fehler beim Parsen von Attribut1!";
			break;
		case produktAttr2:
			errMessage += "Fehler beim Parsen von Attribut2!";
			break;
		case auftragsArt:
			errMessage += "Fehler beim Parsen der Auftragsart!";
			break;
		}
	}
	public String getMessage() {
		return errMessage;
	}
}
