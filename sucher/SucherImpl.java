package sucher;

class SucherImpl extends Thread implements Sucher {
    private final Territorium territorium;
    private int[] aktuellePosition;
    private Richtung gehRichtung = null;

    SucherImpl(int[] neuePosition, Richtung richtung, Territorium territorium) {
        this.aktuellePosition = new int[]{neuePosition[0], neuePosition[1]};
        this.territorium = territorium;
        this.gehRichtung = richtung;
    }

    @Override
    public void setzeGehrichtung(Richtung r) {
        this.gehRichtung = r;
    }

    @Override
    public Richtung gibGehRichtung() {
        return this.gehRichtung;
    }


    @Override
    public Territorium gibTerritorium() {
        return territorium;
    }

    private void bewege(Richtung r) {
        aktuellePosition = gibTerritorium().gibNeuePosition(r, aktuellePosition);
        this.gehRichtung = r;
    }

    @Override
    public int[] gibPosition() {
        return new int[]{aktuellePosition[0], aktuellePosition[1]};
    }


    @Override
    public boolean setzePosition(int[] position) {
        if (territorium.istKachel(position)) {
            this.aktuellePosition = new int[]{position[0], position[1]};
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean bewege() {
        int[] moeglichePosition = gibTerritorium().gibNeuePosition(gibGehRichtung(), gibPosition());

        if (territorium.istKachel(moeglichePosition)) {
            bewege(gibGehRichtung());
            return true;
        } else {
            return false;
        }
    }


}
