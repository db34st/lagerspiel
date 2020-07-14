package UnitTests;
import steuerung.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class DateiEinlesen {
	CsvReader abc;
	String[] zeile;
	@Test
	void printCsvDatei() {
		abc = new CsvReader();
		for(int n = 0; n < 20; n++) {
			zeile = abc.getNaechsteZeile();
			for(int i = 0; i < zeile.length; i++) 
				System.out.print(zeile[i] + "\t\t");
			System.out.print('\n');
		}
	}
}
