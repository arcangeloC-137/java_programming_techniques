package it.polito.tdp.poweroutages.DAO;

import java.util.Comparator;

import it.polito.tdp.poweroutages.model.Blackout;

public class comparatoreBlackout implements Comparator<Blackout> {

	@Override
	public int compare(Blackout o1, Blackout o2) {
		return o1.getData_inizio().compareTo(o2.getData_inizio());
	}

}
