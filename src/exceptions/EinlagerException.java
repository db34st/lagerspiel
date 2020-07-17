package exceptions;
import enums.*;

public class EinlagerException extends Exception{
	ursache u;
	String errMessage = "Fehler beim Einlagern: ";
	public EinlagerException(ursache u) {
		super();
		this.u = u;
		switch(u) {
		case schonVoll:
			errMessage += "Regalfach ist bereits voll!";
			break;
		case nochLeer:
			errMessage += "Regalfach ist leer!";
			break;
		case schwererSteinMussUnten:
			errMessage += "Ein schwerer Stein muss in unterste Regalreihe eingelagert werden!";
			break;
		case holzBalkenBrauchtDrei:
			errMessage += "Ein Holzbalken braucht ein ganz leeres Regalfach mit drei freien Plätzen hintereinander!";
			break;
		}
	}
	public String getMessage() {
		return errMessage;
	}
}