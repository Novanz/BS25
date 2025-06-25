package sucher;
import java.util.concurrent.CountDownLatch;

	private int maxKoernerZahl;
	// hier fehlt ein Attribut zur Synchronisation der Sucher

	KoernerMaximal(int[] aktuellePosition, Richtung richtung, 
			Territorium territorium
			// hier fehlt ein Parameter zur Synchronisation der Sucher
			) {
		super(aktuellePosition, richtung, territorium);
		
		// die Attribute initialisieren
	}

	private int gibMaxKoernerZahl() {
		return this.maxKoernerZahl;
	}

	public void run() {
		boolean istKachel = true;
		
		do {
			bearbeiteKachel();
			istKachel = bewege();
		} while (istKachel);

		// Auftrag erledigt
		
		// hier muss etwas programmiert werden, dass dem main
		// Thread signalisiert, dass einer von beiden Threads
		// die groesste Koernerzahl von seinen Kacheln 
		// herausgefunden hat
	}

	private void bearbeiteKachel() {
		// hier muss die KoernerZahl der aktuellen Kachel mit der
		// bisher groessten Koernerzahl (maxKoernerZahl) verglichen
		// werden. Was soll passieren, wenn die aktuelle Koernerzahl
		// groesser ist, als maxKoernerZahl?
	}

	// Konfiguration Territorium
	private static int[][] koernerZahlen = { { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 2, 0, 0, 0, 6, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 3, 0, 6, 2, 0, 0, 6, 7, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0 }, };

	public static void main(String[] args) {
		Territorium territorium = new Territorium(koernerZahlen);

		CountDownLatch zaehler = new CountDownLatch(2);
		int[] aktuellePosition = new int[] { 3, 4 };
		KoernerMaximal sucherLinks;
		KoernerMaximal sucherRechts;

		// zwei helfende Sucher muessen hier erzeugt und
		// auf die Suche geschickt d.h. gestartet werden;
		// einer nach links, einer nach rechts
		
		
		// nun wartet der main Thread darauf,
		// dass die beiden Sucher fertig werden
		
		while (sucherLinks.isAlive() && sucherRechts.isAlive()) {
            // diese Schleife tut nichts ("aktives Warten").
			// Warum ist diese Schleife schlecht?
			// Sie sollen diese Schleife hier ersetzen. Dies
			// soll mithilfe eines CountDownLatch passieren.
		}

		int maxKoernerZahl = 0;
		
		// nun sind beide fertig. Die MaxKoerner Zahlen der beiden
		// Threads koennen verglichen werden
		// und das Endergebnis kann
		// ermittelt und verkuendet werden
		
		System.out.println("Die maximale Anzahl an Koernern" + " auf Kacheln in\n"
				+ "der Reihe, in der ich stehe, beträgt: " + maxKoernerZahl );
	}

}
