package sucher;


interface Sucher {
	boolean setzePosition(int[] position);
	Territorium gibTerritorium();
	boolean bewege();
	int[] gibPosition();
	Richtung gibGehRichtung();
	void setzeGehrichtung(Richtung r);
}
