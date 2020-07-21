package exceptions;
import enums.*;

@SuppressWarnings("serial")
public class RegalException extends Exception{
	String errMessage = "Fehler im Regal: ";
	public RegalException(Ursache u) {
		super();
		switch(u) {
		case schonVoll:
			errMessage += "Regalfach ist bereits voll!";
			break;
		case regalLeer:
			errMessage += "Regalfach ist leer!";
			break;
		case schwererSteinMussUnten:
			errMessage += "Ein schwerer Stein muss in unterste Regalreihe eingelagert werden!";
			break;
		case holzBalkenBrauchtDrei:
			errMessage += "Ein Holzbalken braucht ein ganz leeres Regalfach mit drei freien Plätzen hintereinander!";
			break;
		default:
			break;
		}
	}
	public String getMessage() {
		return errMessage;
	}
}