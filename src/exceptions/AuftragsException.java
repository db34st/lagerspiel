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
		}
		
	}
	public String getMessage() {
		return errMessage;
	}
}
