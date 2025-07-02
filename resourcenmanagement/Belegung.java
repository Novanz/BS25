package resourcenmanagement;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

//import vollstaendigeVersion.Beleger;

/**
 * Eine Belegung fasst alle Beleger (Threads) zusammen, die 
 * aktuell noch nicht fertig sind. Der Resourcenmanager versucht,
 * einen Thread zu finden, der aktuell fertig werden kann. Eine neue
 * Belegung entsteht dann dadurch, dass der Thread, der fertig werden 
 * kann aus der alten Belegung entfernt wird. Die neue Belegung wird
 * in der alten Belegung als kindbelegung Attribut eingetragen.
 * @author Glaeser
 *
 */
class Belegung {
	/**
	 * dieBeleger = alle Threads, die noch nicht fertig geworden sind.
	 * Wenn dieBeleger leer ist, dann sind alle Threads fertig geworden.
	 * Dies ist die erwuenschte Endbelegung. Wenn diese Endbelegung
	 * erreicht werden kann, ist die Ausgangsbelegung sicher
	 */
	private Set<Beleger> dieBeleger;

	/**
	 *  in kindbelegung wird diejenige Belegung eingetragen, die entsteht,
	 *  wenn ein Thread fertig werden konnte
	 */
	private Belegung kindbelegung = null;

	Belegung(Set<Beleger> beleger) {
		this.dieBeleger = new LinkedHashSet<>(beleger);
	}
	
	Set<Beleger> gibBeleger() {
		return this.dieBeleger;
	}

	/**
	 * Ausgehend von dieser Belegung (Ausgangsbelegung) 
	 * wird nach dem erst besten
	 * Thread gesucht, der fertig werden kann. Dieser Thread wird aus
	 * der Belegung entfernt. Dadurch entsteht eine "Kindbelegung".
	 * Diese wird in kindbelegung eingetragen. Ausgehend von der 
	 * Kindbelegung wird rekursiv weiter nach Threads gesucht, die
	 * fertig werden koennen. Wenn alle Threads fertig werden koennen,
	 * also eine leere Belegung erreicht werden kann, dann ist die 
	 * Ausgangsbelegung sicher, sonst nicht.
	 * @param zahlFreierBetriebsmittel
	 * @return
	 */
	// fertig programmieren ______________________________________________________________
	boolean istSicher(int zahlFreierBetriebsmittel) {
		// Wenn dieBeleger leer sind, dann true zurueckgeben
		if (this.dieBeleger.isEmpty()) {
			
			// alle Threads sind fertig geworden: das zeigt, dass es
			// einen Pfad ohne Verklemmungsgefahr gibt
			// Pfad = Abfolge von nacheinander fertig werdenden Threads
			// muss hier ein Rueckgabewert zurueckgegeben werden?
			// wenn ja: true oder false?
			return true;
		} else {
			// gib es wenigstens einen Thread (Beleger), der garantiert ohne Verklemmung
			// fertig werden kann?
			for (Beleger einBeleger : this.dieBeleger) {
				// in die if Bedigung darf nur ein Beleger hinein, der fertig werden kann
				if (einBeleger.gibRestforderung() <= zahlFreierBetriebsmittel) {
	                // die Beleger der alten Belegung in eine neue Belegung hineinkopieren
	                // den Beleger, der fertig ist, aus dieNeueBelegung entfernen
					Set<Beleger> dieNeueBeleger = new LinkedHashSet<>(this.dieBeleger);
					dieNeueBeleger.remove(einBeleger);
					// zahl der freien Betriebsmittel erhoeht sich um die Zahl der bisher von
					// einBeleger blockierten Betriebsmittel
					int neueZahlFreierBetriebsmittel = zahlFreierBetriebsmittel
							+ einBeleger.gibZahlAktuellBelegterBetriebsmittel();
					// dieNeueBelegung wird im Attribut kindbelegung gespeichert
					Belegung dieNeueBelegung = new Belegung(dieNeueBeleger);
					this.kindbelegung = dieNeueBelegung;
					// ausgehend vom der neuen ("Kind") Belegung wird rekursiv
					// nach weiteren Threads gesucht, die fertig werden koennen
					// das boolsche Ergebnis ist der Rueckgabewert dieser Methode
					if (dieNeueBelegung.istSicher(neueZahlFreierBetriebsmittel)) {
						// wenn die neue Belegung sicher ist, dann ist auch die alte Belegung sicher
						// und es kann true zurueckgegeben werden
						return true;
					}
					// wenn die neue Belegung nicht sicher ist, dann wird mit dem naechsten
					// Beleger weitergemacht

				}
                // Ende fertig programmieren __________________________________________
			}
			// wenn das Programm hier ankommt, dann kann im schlimmsten Fall
			// von der aktuellen Belegung aus kein Thread fertig laufen
			return false;
		}
	}


	/**
	 * Der "Pfad" besteht aus einer Abfolge von Threads. Diese Threads
	 * koennen in der ausgegebenen Pfad-Reihenfolge hintereinander 
	 * fertig werden, ohne das eine Verklemmungsgefahr besteht
	 */
	public void druckePfad() {
		if (this.kindbelegung != null) {
			Set<Beleger> differenzMenge = new HashSet<>(this.dieBeleger);
			differenzMenge.removeAll(this.kindbelegung.dieBeleger);
			// Differenzmenge enthaelt nur einen Beleger, naemlich
			// denjenigen Thread, der gerade fertig laufen kann.
			// Dieser Thread wird als Teil des Pfades ausgegeben
			String belegerName = differenzMenge.iterator().next().gibName();
			System.out.println(belegerName);
			this.kindbelegung.druckePfad();
		}
	}

}
