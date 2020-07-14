package UnitTests;
import steuerung.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import auftraege.*;
import produkte.Produkt;

class DateiEinlesen {
	
	@Test
	void printCsvDatei() {
		 
		String[] zeile;
		CsvReader abc = new CsvReader();
		for(int n = 0; n < 20; n++) {
			zeile = abc.getNaechsteZeile();
			/*
			for(int i = 0; i < zeile.length; i++) 
				System.out.print(zeile[i] + "\t\t");
			System.out.print('\n');
			*/
		}
	}
	
	@Test
	void neuerAuftrag() {
		int zeileInCsvDatei = 6;
		
		Steuerung dieSteuerung = new Steuerung(null);
		Auftragsliste temp;
		String[] kontrolle;
		CsvReader abc = new CsvReader();		
		
		kontrolle = abc.getNaechsteZeile();
		temp = dieSteuerung.neuerAuftrag();
		
		for(int m = 1; m < zeileInCsvDatei; m++) {
			kontrolle = abc.getNaechsteZeile();
			temp = dieSteuerung.neuerAuftrag();
		}
		
		for(int i = 0; i < kontrolle.length; i++) 
			System.out.print(kontrolle[i] + "\t\t");
		
		assertEquals(temp.elem().getProdukt().getProduktName(), kontrolle[2]);
		assertEquals(temp.elem().getProdukt().getAttribut1(), kontrolle[3]);
		assertEquals(temp.elem().getProdukt().getAttribut2(), kontrolle[4]);
		assertEquals(Integer.toString(temp.elem().getBelohnung()), kontrolle[5]);
		assertEquals(temp.elem().getAuftragsArt(), kontrolle[1]);
		temp.advance();
	}
	
	
}
