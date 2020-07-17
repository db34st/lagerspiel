package exceptions;

import enums.ursache;

public class AuftragsException extends Exception{
	String errMessage = "Auftragsfehler: ";
	public AuftragsException(ursache u) {
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
