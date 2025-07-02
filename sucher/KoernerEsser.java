package sucher;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class KoernerEsser extends SucherImpl implements Runnable {

    private final CyclicBarrier barriere;
    private int zahlEsser = 0;

    KoernerEsser(int[] neuePosition, Richtung richtung, Territorium territorium,
                 int zahlEsser, CyclicBarrier barriere // hier fehlt ein Parameter - welcher?
    ) {
        super(neuePosition, richtung, territorium);
        this.barriere = barriere;
        this.zahlEsser = zahlEsser;
    }

    private static final int[][] zahlKoerner = {
            {1, 0, 2, 0, 3, 0, 4, 0, 5, 0},
            {6, 0, 7, 0, 8, 0, 9, 0, 10, 0},
            {1, 0, 2, 0, 3, 0, 4, 0, 5, 0},
            {6, 0, 7, 0, 8, 0, 9, 0, 10, 20}};

    public static void main(String[] args) {
        Territorium territorium = new Territorium(zahlKoerner);
        int zahlEsser = 3;
        // Hier muss ein Thread Pool mit einer festen Anzahl von Threads
        // erzeugt werden. Wie viele Threads muessen es sein?
        // Warum duerfen es nicht weniger sein, als die zahl der Esser?
        ExecutorService dienstleister = Executors.newFixedThreadPool(zahlEsser);
        // hier muss noch eine CyclicBarrier erzeugt werden
        // bekommt jeder KoernerEsser seine eigene CyclicBarrier
        // oder jeder dieselbe?
        // Mit welchem Wert muss die CyclicBarrier erzeugt werden?
        CyclicBarrier barriere = new CyclicBarrier(zahlEsser);

        for (int i = 0; i < zahlEsser; i++) {
            KoernerEsser esser = new KoernerEsser(
                    new int[]{0, 0}, Richtung.RECHTS, territorium, zahlEsser, barriere
            );
            // hier muessen die Esser dem ThreadPool zur Ausführung uebergeben werden
            dienstleister.execute(esser);
        }

        dienstleister.shutdown();
        // hier soll der main Thread so lange warten, bis der letzte Auftrag
        // des Thread Pool abgearbeitet ist. Dies gelingt mit einer
        // Methode von ExecutorService
        try {
            dienstleister.awaitTermination(Long.MAX_VALUE, java.util.concurrent.TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }

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
            istKachel = this.setzePosition(new int[]{zeile, 0});
        } while (istKachel);
    }

    private void bearbeiteKachel() {
        // Mit gibTerritorium(), das von SucherImpl geerbt wird, kann
        // man das aktuelle Territorium erhalten. Dort kann man
        // mit einer ebenfalls geerbten Methode die Zahl der Koerner
        // bei der aktuellen Kachel erhalten.
        int aktuelleKoerner = gibTerritorium().gibKoerner(gibPosition());
        // Dann muss man, wenn die Zahl der Koerner groesser ist, als die
        // Zahl der Esser, die Zahl der Koerner um eins verringern.
        // Mit welcher Methode von Territorium kann man die Zahl der
        // Koerner um eins verringern?
        try {
            barriere.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (aktuelleKoerner >= zahlEsser ) {
            gibTerritorium().dekrementiereKoerner(gibPosition());
        }

        // Wie kann man die CyclicBarrier verwenden, damit jeder Esser
        // die ursprüngliche Zahl der Koerner sieht?
    }


}
