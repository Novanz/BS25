package sucher;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class KoernerZaehler extends SucherImpl implements Callable<Integer> {

    private static final int[][] koernerZahlen = {
            {1, 0, 2, 0, 3, 0, 4, 0, 5, 0},
            {6, 0, 7, 0, 8, 0, 9, 0, 10, 0},
            {1, 0, 2, 0, 3, 0, 4, 0, 5, 0},
            {6, 0, 7, 0, 8, 0, 9, 0, 10, 17}};

    public static void main(String[] args) {
        Territorium territorium = new Territorium(koernerZahlen);
        final int ANZAHL_SUCHER = territorium.gibZeilenZahl();
        final int ANZAHL_GLEICHZEITIGE_THREADS = 2;
        ExecutorService pool = Executors.newFixedThreadPool(ANZAHL_GLEICHZEITIGE_THREADS);
        Set<Future<Integer>> dieFutures = new LinkedHashSet<>();

        // in einer Schleife wird fuer jede Zeile eine KoernerZaehler Objekt erzeugt.
        // Wenn das Objekt in einer Objektreferenz gespeichert wird, dann sollte diese Objektreferenz den Typ
        // Callable<Integer> und NICHT KoernerZaehler haben.
        // Dieses Objekt wir mit der Methode submit dem Threadpool zur Ausführung übergeben.
        // submit liefert ein Future<Integer> Objekt zurueck, das in dem Set dieFutures gespeichert werden muss.

        // Erstellt für jede Zeile einen KoernerZaehler und übergibt ihn dem Threadpool zur Ausführung.
        for (int i = 0; i < ANZAHL_SUCHER; i++) {
            // Erzeugt ein neues Callable für jede Zeile des Territoriums.
            Callable<Integer> zaehler = new KoernerZaehler(territorium, new int[]{i, 0}, Richtung.RECHTS);
            // Der ExecutorService übergibt das Callable an den Threadpool, und ruft call() auf den zaehler, dann
            // speichert das zurückgegebene Future.
            Future<Integer> future = pool.submit(zaehler);
            dieFutures.add(future);
        }
        pool.shutdown();

        int anzahlKoerner = 0;

        // in einer Schleife holt man ein Future Objekt nach dem anderen aus dem Set dieFutures.
        // Dann ruft man auf jedem dieser Future Objekte die Methode get() auf.
        // Diese Method liefert die Zahl von Koernern, die es insgesamt in der Zeile des zugehörigen Suchers gibt.
        // Diese Zahlen muessen in anzahlKoerner aufaddiert werden.

        // Wartet auf die Beendigung aller Threads und sammelt die Ergebnisse.
        try {
            for (Future<Integer> future : dieFutures) {
                // get() blockiert, bis das Ergebnis des Threads verfügbar ist. Siehe API.
                // Das Ergebnis wird zur Gesamtsumme addiert.
                anzahlKoerner += future.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Die Anzahl der Koerner beträgt: " + anzahlKoerner);
    }

    KoernerZaehler(Territorium territorium, int[] startPunkt, Richtung gehRichtung) {
        super(startPunkt, gehRichtung, territorium);
    }

    @Override
    public Integer call() throws Exception {
        boolean istKachel = true;

        do {
            bearbeiteKachel();
            istKachel = bewege();
        } while (istKachel);

        return gesamtZahlKoerner;
    }

    private int gesamtZahlKoerner = 0;

    private void bearbeiteKachel() {
        // im Attribut gesamtZahlKoerner werden die koerner Zahlen
        // der einzelnen Kacheln in der Zeile aufaddiert.
        // mit gibTerritorium() kann man sich das zum Sucher gehörige
        // Territorium Objekt holen. Mit gibKoerner kann man sich die
        // Koernerzahl der aktuellen Kachel holen. gibPosition gibt die
        // Position der aktuellen Kachel des Suchers zurueck.
        gesamtZahlKoerner += gibTerritorium().gibKoerner(gibPosition());
    }

}
