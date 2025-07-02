package sucher;

import java.util.concurrent.CountDownLatch;

class KoernerMaximal extends SucherImpl implements Runnable {
    // Konfiguration Territorium
    private static final int[][] koernerZahlen = {{0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 2, 0, 0, 0, 6, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 3, 0, 6, 2, 0, 0, 6, 7, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0},};
    // hier fehlt ein Attribut zur Synchronisation der Sucher
    private final CountDownLatch zaehler;
    private int maxKoernerZahl;

    KoernerMaximal(int[] aktuellePosition, Richtung richtung,
                   Territorium territorium,
                   CountDownLatch zaehler // hier fehlt ein Parameter zur Synchronisation der Sucher
    ) {
        super(aktuellePosition, richtung, territorium);
        this.zaehler = zaehler;
    }

    public static void main(String[] args) {
        Territorium territorium = new Territorium(koernerZahlen);

        CountDownLatch zaehler = new CountDownLatch(2);
        int[] aktuellePosition = new int[]{3, 4};
        KoernerMaximal sucherLinksi = new KoernerMaximal(aktuellePosition, Richtung.LINKS, territorium, zaehler);
        KoernerMaximal sucherRechts = new KoernerMaximal(aktuellePosition, Richtung.RECHTS, territorium, zaehler);

        // zwei helfende Sucher muessen hier erzeugt und
        // auf die Suche geschickt d.h. gestartet werden;
        // einer nach links, einer nach rechts
        new Thread(sucherLinksi).start();
        new Thread(sucherRechts).start();

        try {
            // hier wartet der main Thread darauf, dass die beiden Sucher fertig werden
            zaehler.await(); // wartet, bis der CountDownLatch auf 0 ist
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//		while (sucherLinks.isAlive() && sucherRechts.isAlive()) {
        // diese Schleife tut nichts ("aktives Warten").
        // Warum ist diese Schleife schlecht?

        // nun sind beide fertig. Die MaxKoerner Zahlen der beiden Threads koennen verglichen werden
        // und das Endergebnis kann ermittelt und verkuendet werden
        int maxKoernerZahl = Math.max(sucherLinksi.gibMaxKoernerZahl(), sucherRechts.gibMaxKoernerZahl());

        System.out.println("Die maximale Anzahl an Koernern" + " auf Kacheln in\n"
                + "der Reihe, in der ich stehe, beträgt: " + maxKoernerZahl);
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
        zaehler.countDown(); // hier wird der CountDownLatch um eins verringert
        // hier muss etwas programmiert werden, dass dem main Thread signalisiert, dass einer von beiden Threads
        // die groesste Koernerzahl von seinen Kacheln herausgefunden hat
    }

    private void bearbeiteKachel() {
        // hier muss die KoernerZahl der aktuellen Kachel mit der
        // bisher groessten Koernerzahl (maxKoernerZahl) verglichen
        // werden. Was soll passieren, wenn die aktuelle Koernerzahl groesser ist, als maxKoernerZahl?
        int aktuelleKoernerZahl = gibTerritorium().gibKoerner(gibPosition()); // Koernerzahl der aktuellen Kachel holen
        if (aktuelleKoernerZahl > maxKoernerZahl) {
            maxKoernerZahl = aktuelleKoernerZahl;
        }
    }

}
