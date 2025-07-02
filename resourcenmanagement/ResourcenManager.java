package resourcenmanagement;

import java.util.HashSet;
import java.util.Set;


class ResourcenManager {
	
	public static void main(String[] args) {
		Beleger threadA = new Beleger("A", 6, 3);
		Beleger threadB = new Beleger("B", 2, 2);
		Beleger threadC = new Beleger("C", 5, 2);
		
		Set<Beleger> dieBeleger =  new HashSet<Beleger>();
		dieBeleger.add(threadA);
		dieBeleger.add(threadB);
		dieBeleger.add(threadC);
		
		Belegung ausgangsBelegung = new Belegung(dieBeleger);
		int gesamtZahlBetriebsmittel = 10;
		ResourcenManager manager = new ResourcenManager(gesamtZahlBetriebsmittel);
    	
    	if (manager.belegungIstSicher(ausgangsBelegung)) {
    		System.out.println("Ist sicher");
    		ausgangsBelegung.druckePfad();
    	} else {
    		System.out.println("Ist nicht sicher");
    	}
	}
	
	private int gesamtZahlBetriebsmittel;
	
	ResourcenManager(int gesamtZahlBetriebsmittel ) {
		this.gesamtZahlBetriebsmittel = gesamtZahlBetriebsmittel;
	}
	
	private int berechneZahlFreierBetriebsmittel(Belegung belegung) {
		int zahlFreierBetriebsmittel = this.gesamtZahlBetriebsmittel;
		for (Beleger beleger : belegung.gibBeleger()) {
			zahlFreierBetriebsmittel -= beleger.gibZahlAktuellBelegterBetriebsmittel();
		}
		return zahlFreierBetriebsmittel;
	}

	
	
	boolean belegungIstSicher(Belegung belegung) {
		int zahlFreierBetriebsmittel = this.berechneZahlFreierBetriebsmittel(belegung);
    	boolean istSicher = belegung.istSicher(zahlFreierBetriebsmittel);
    	return istSicher;
    }
}
