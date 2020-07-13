package UnitTests;
import produkte.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class produkte {
	Produkt[][] papier  = new Produkt[3][3];
	String[][] attrPapier = { {"Weiss", "A3"}, {"Gruen", "A4" }, {"Blau", "A5"}};
	
	Produkt[][] holz  = new Produkt[3][3];
	String[][] attrHolz = { {"Kiefer", "Bretter"}, {"Buche", "Balken" }, {"Eiche", "Scheit"}};
	
	Produkt[][] stein  = new Produkt[3][3];
	String[][] attrStein = { {"Marmor", "Leicht"}, {"Granit", "Mittel" }, {"Sandstein", "Schwer"}};
	
	public produkte() {
		for(int n = 0; n<3; n++) 
			for(int m = 0; m<3; m++)
				papier[n][m] = new Papier(attrPapier[m][0], attrPapier[n][1]);
		for(int n = 0; n<3; n++) 
			for(int m = 0; m<3; m++) 
				holz[n][m] = new Holz(attrHolz[m][0], attrHolz[n][1]);
		for(int n = 0; n<3; n++) 
			for(int m = 0; m<3; m++) 
				stein[n][m] = new Stein(attrStein[m][0], attrStein[n][1]);
	}
	@Test
	void papierEinlagerbar() {
		assertEquals(papier[0][0].pruefeObEinlagerbar(0, 0, 0), false);
		assertEquals(papier[0][0].pruefeObEinlagerbar(0, 0, 2), true);
		assertEquals(papier[0][0].pruefeObEinlagerbar(1, 1, 3), true);
		assertEquals(papier[0][0].pruefeObEinlagerbar(2, 1, 1), true);
	}
	@Test
	void holzEinlagerbar() {
		assertEquals(holz[1][0].pruefeObEinlagerbar(0, 0, 2), false);
		assertEquals(holz[1][0].pruefeObEinlagerbar(1, 0, 0), false);
		assertEquals(holz[1][0].pruefeObEinlagerbar(0, 2, 1), false);
		assertEquals(holz[1][0].pruefeObEinlagerbar(2, 0, 3), true);
		assertEquals(holz[1][0].pruefeObEinlagerbar(0, 1, 3), true);
		assertEquals(holz[1][0].pruefeObEinlagerbar(0, 0, 0), false);
	}
	@Test
	void steinEinlagerbar() {
		//schwere steine
		assertEquals(stein[2][2].pruefeObEinlagerbar(0, 2, 0), false);
		assertEquals(stein[2][2].pruefeObEinlagerbar(2, 2, 2), true);
		assertEquals(stein[2][2].pruefeObEinlagerbar(1, 2, 3), true);
		assertEquals(stein[2][2].pruefeObEinlagerbar(0, 2, 2), true);
		assertEquals(stein[2][2].pruefeObEinlagerbar(0, 2, 2), true);
		assertEquals(stein[2][2].pruefeObEinlagerbar(0, 1, 2), false);
		assertEquals(stein[2][2].pruefeObEinlagerbar(0, 0, 2), false);
		assertEquals(stein[2][2].pruefeObEinlagerbar(1, 0, 2), false);
		//nicht schwer
		assertEquals(stein[0][0].pruefeObEinlagerbar(0, 0, 0), false);
		assertEquals(stein[0][0].pruefeObEinlagerbar(0, 0, 2), true);
		assertEquals(stein[0][0].pruefeObEinlagerbar(1, 1, 3), true);
		assertEquals(stein[0][0].pruefeObEinlagerbar(2, 1, 1), true);
	}
}
