package resourcenmanagement;


/**
 * Ein Beleger kann z.B. Thread A sein
 * @author Glaeser
 *
 */
class Beleger {
	/**
	 * maximaleForderung = hoechste Anzahl von Betriebsmitteln,
	 * die der Thread im schlimmsten Fall gleichzeitig benoetigt,
	 * um nicht blockiert zu sein
	 */
	private final int maximaleForderung;
	
	/**
	 * name = Name des Threads bzw. Belegers
	 */
	private String name;
	/**
	 * zahlAktuellBelegterBetriebsmittel = Anzahl von Betriebsmitteln,
	 * die der Thread im Augenblick gerade blockiert
	 */
	private int zahlAktuellBelegterBetriebsmittel = 0;


	Beleger(String name, int maximaleForderung, int zahlAktuelleBelegterBetriebsmittel) {
		this.name = name;
		this.maximaleForderung = maximaleForderung;
		this.zahlAktuellBelegterBetriebsmittel = zahlAktuelleBelegterBetriebsmittel;
	}
	
	int gibZahlAktuellBelegterBetriebsmittel() {
		return zahlAktuellBelegterBetriebsmittel;
	}

	/**
	 * Die Restforderung ist die maximale Anzahl an Betriebsmitteln,
	 * die ein Thread aktuell im schlimmsten Fall braucht, um
	 * weiterarbeiten zu koennen
	 * @return
	 */
	int gibRestforderung() {
		return this.maximaleForderung - this.zahlAktuellBelegterBetriebsmittel;
	}

	int gibMaximaleForderung() {
		return this.maximaleForderung;
	}

	String gibName() {
		return name;
	}
	
	public String toString() {
		return this.name;
	}

}
