package it.polito.tdp.rivers.model;

import it.polito.tdp.rivers.db.RiversDAO;

public class TestSimulator {

	public static void main(String[] args) {
		
		Simulator sim = new Simulator();
		RiversDAO dao = new RiversDAO();
		River fiume = dao.getAllRivers().get(0);
		
		sim.run(fiume, 1.9, dao.getAvgFlow(fiume));
		
		System.out.println("\n\nCapacità media del bacino: "+sim.getCapacitaMedia()+
				"\nGiorni in cui non è stato possibile erogare la quantità minima di flusso: "+sim.getGiorniNotErogazioneMin());
	}

}
