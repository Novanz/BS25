package sucher;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;


class KoernerEsser extends SucherImpl implements Runnable {
	
	private CyclicBarrier barriere = null;
	private int zahlEsser = 0;

	KoernerEsser(int[] neuePosition, Richtung richtung, Territorium territorium,
			 int zahlEsser // hier fehlt ein Parameter - welcher?
			 ) {
		super(neuePosition, richtung, territorium);
		// hier muss das barriere Attribut initialisiert werden
		this.zahlEsser = zahlEsser;
	}

	private static int[][] zahlKoerner = { 
			{ 1, 0, 2, 0, 3, 0, 4, 0, 5, 0 }, 
			{ 6, 0, 7, 0, 8, 0, 9, 0, 10, 0 },
			{ 1, 0, 2, 0, 3, 0, 4, 0, 5, 0 }, 
			{ 6, 0, 7, 0, 8, 0, 9, 0, 10, 20 } };

	public static void main(String[] args) {
		Territorium territorium = new Territorium(zahlKoerner);
		int zahlEsser = 3;
		// Hier muss ein Thread Pool mit einer festen Anzahl von Threads
		// erzeugt werden. Wie viele Threads muessen es sein?
		// Warum duerfen es nicht weniger sein, als die zahl der Esser?
		ExecutorService dienstleister; 
		// hier muss noch eine CyclicBarrier erzeugt werden
		// bekommt jeder KoernerEsser seine eigene CyclicBarrier
		// oder jeder dieselbe?
		// Mit welchem Wert muss die CyclicBarrier erzeugt werden?
		for (int i = 0; i < zahlEsser; i++) {
			KoernerEsser esser = new KoernerEsser(
					new int[] {0,0}, Richtung.RECHTS,territorium, zahlEsser
					// hier fehlt ein Übergabeparameter - welcher?
					);
			// hier muessen die Esser dem ThreadPool zur Ausführung
			// uebergeben werden
		}
		
		dienstleister.shutdown();
		// hier soll der main Thread so lange warten, bis der letzte Auftrag
		// des Thread Pool abgearbeitet ist. Dies gelingt mit einer
		// Methode von ExecutorService
		
		
		territorium.gibKoernerAus();
	}
	
	public void run() {
		int zeile = 0;
		boolean istKachel = true;
		do {
			
			do {
				bearbeiteKachel();
				istKachel = bewege();
			} while (istKachel);
			
			zeile++;
			istKachel = this.setzePosition(new int[] {zeile, 0});
		} while(istKachel);
	}

	private void bearbeiteKachel() {
		// Mit gibTerritorium(), das von SucherImpl geerbt wird, kann
		// man das aktuelle Territorium erhalten. Dort kann man
		// mit einer ebenfalls geerbten Methode die Zahl der Koerner
		// bei der aktuellen Kachel erhalten. 
		
		// Dann muss man, wenn die Zahl der Koerner groesser ist, als die
		// Zahl der Esser, die Zahl der Koerner um eins verringern.
		// Mit welcher Methode von Territorium kann man die Zahl der 
		// Koerner um eins verringern?
		
		// Wie kann man die CyclicBarrier verwenden, damit jeder Esser
		// die ursprüngliche Zahl der Koerner sieht?
	}
	

}
