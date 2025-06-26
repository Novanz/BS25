package sucher;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

class Territorium {

    // jede Position, die keine Kachel ist, ist eine Mauer
    private boolean[][] istKachel = null;

    private AtomicInteger[][] zahlenKoerner = null;

    // Konstruktor fuer Territorium ohne Koerner aber mit Mauern
    Territorium(boolean[][] istKachel) {
        this.istKachel = istKachel;
    }

    // Konstruktor fuer Territorium ohne Mauern aber mit Koernern
    Territorium(int[][] zahlKoerner) {
        istKachel = new boolean[zahlKoerner.length][zahlKoerner[0].length];
        zahlenKoerner = new AtomicInteger[zahlKoerner.length][zahlKoerner[0].length];
        for (int i = 0; i < zahlKoerner.length; i++) {
            for (int j = 0; j < zahlKoerner[0].length; j++) {
                zahlenKoerner[i][j] = new AtomicInteger(zahlKoerner[i][j]);
                istKachel[i][j] = true;
            }
        }
    }


    boolean istAusserhalb(int[] position) {
        if (position[0] < 0 || position[0] >= istKachel.length) {
            return true;
        } else return position[1] < 0 || position[1] >= istKachel[0].length;
    }

    Richtung gibGegenrichtung(Richtung r) {
        if (r == null) {
            return null;
        }
        if (r.equals(Richtung.OBEN)) {
            return Richtung.UNTEN;
        } else if (r.equals(Richtung.RECHTS)) {
            return Richtung.LINKS;
        } else if (r.equals(Richtung.UNTEN)) {
            return Richtung.OBEN;
        } else if (r.equals(Richtung.LINKS)) {
            return Richtung.RECHTS;
        } else {
            return null;
        }
    }


    int gibKoerner(int[] position) {
        if (zahlenKoerner == null) {
            return 0;
        } else {
            return zahlenKoerner[position[0]][position[1]].get();
        }

    }

    static boolean[][] wandleIntegerInBoolean(int[][] kachelnInt) {
        boolean[][] istKachel = new boolean[kachelnInt.length][kachelnInt[0].length];
        int zeilenNr = 0;
        for (int[] zeile : kachelnInt) {
            int spaltenNr = 0;
            for (int istKachelInt : zeile) {
                istKachel[zeilenNr][spaltenNr] = (istKachelInt == 1);
                spaltenNr++;
            }
            zeilenNr++;
        }
        return istKachel;
    }


    boolean istKachel(int[] position) {
        if (istAusserhalb(position)) {
            return false;
        } else {
            return istKachel[position[0]][position[1]];
        }
    }

    Queue<Richtung> gibVerzweigungen(Sucher sucher) {
        Queue<Richtung> verzweigungen = new LinkedList<>();
        if (verzweigungExistiert(Richtung.OBEN, sucher)) {
            verzweigungen.add(Richtung.OBEN);
        }
        if (verzweigungExistiert(Richtung.RECHTS, sucher)) {
            verzweigungen.add(Richtung.RECHTS);
        }
        if (verzweigungExistiert(Richtung.UNTEN, sucher)) {
            verzweigungen.add(Richtung.UNTEN);
        }
        if (verzweigungExistiert(Richtung.LINKS, sucher)) {
            verzweigungen.add(Richtung.LINKS);
        }
        return verzweigungen;
    }

    // wenn r == null, wird jede Verzweigungsrichtung als true gewertet,
    // also auch die, aus der der Sucher eventuell gekommen ist.
    // Ist wichtig, wenn der Sucher gerade seine Suche beginnt, und
    // von nirgendwo hergekommen ist.
    private boolean verzweigungExistiert(Richtung r, Sucher sucher) {
        int[] moeglichePosition = gibNeuePosition(r, sucher.gibPosition());
        return this.istKachel(moeglichePosition) && !r.equals(gibGegenrichtung(sucher.gibGehRichtung()));
    }


    public int[] gibNeuePosition(Richtung r, int[] altePosition) {
        int[] neuePosition = new int[]{altePosition[0], altePosition[1]};
        switch (r) {
            case OBEN:
                neuePosition[0] = altePosition[0] - 1;
                break;
            case RECHTS:
                neuePosition[1] = altePosition[1] + 1;
                break;
            case UNTEN:
                neuePosition[0] = altePosition[0] + 1;
                break;
            case LINKS:
                neuePosition[1] = altePosition[1] - 1;
                break;
        }
        return neuePosition;
    }


    void dekrementiereKoerner(int[] position) {
        zahlenKoerner[position[0]][position[1]].decrementAndGet();
    }

    void setzeKoernerZahl(int[] position, int zahlKoerner) {
        zahlenKoerner[position[0]][position[1]].set(zahlKoerner);
    }

    public void gibKoernerAus() {
        for (AtomicInteger[] zeile : zahlenKoerner) {
            for (AtomicInteger zahlKoerner : zeile) {
                System.out.print(zahlKoerner.get() + "\t");
            }
            System.out.println();
        }

    }

    int gibZeilenZahl() {
        return istKachel.length;
    }

    int gibSpaltenZahl() {
        return istKachel[0].length;
    }

}
