package afleveringsopgave4.opgave1.Lauszus;

public class Forlag {
	String navn;
	String sted;
	
	/**
	 * Skriv kommentarer her
	 * @param name
	 * @param location
	 */
	public Forlag(String navn, String sted) {
		this.navn = navn;
		this.sted = sted;
	}
	
	public String toString() {
		return (navn + ", " + sted);
	}
}
