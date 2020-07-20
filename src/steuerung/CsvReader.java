package steuerung;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CsvReader {
	String csvFile="Leistungsnachweis.csv";
	FileReader fr;
	BufferedReader br;
	int anzSpalten = 1;
	public CsvReader(){
		try {
		fr = new FileReader(csvFile);
	    br = new BufferedReader(fr);
	    String zeile = br.readLine();
	    for (int n = 0; n < zeile.length();n++)
	    	if(zeile.charAt(n) == ';')
	    		anzSpalten++;
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	public String[] getNaechsteZeile(){
		String[] temp = new String[anzSpalten];
		for(int i = 0; i < temp.length; i++) {
			temp[i] = "";
		}
		try {
			String zeile = br.readLine();
			if(zeile == null) {
				br.close();
				fr = new FileReader(csvFile);
				br = new BufferedReader(fr);
				br.readLine();
				zeile = br.readLine();
			}
			int i = 0;
			for(int n = 0; n < zeile.length(); n++)
				if(zeile.charAt(n) != ';')
					temp[i]+=zeile.charAt(n);
				else
					i++;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return temp;
	}
	protected void finalize() throws IOException{
	   br.close();
	}
}