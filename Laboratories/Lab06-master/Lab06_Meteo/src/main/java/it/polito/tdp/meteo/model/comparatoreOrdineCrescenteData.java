package it.polito.tdp.meteo.model;

import java.util.Comparator;

public class comparatoreOrdineCrescenteData implements Comparator<Rilevamento> {

	@Override
	public int compare(Rilevamento o1, Rilevamento o2) {
		
		return o1.getData().compareTo(o2.getData());
	}

}
