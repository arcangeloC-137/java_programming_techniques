package it.polito.tdp.libretto.model;

import java.util.Comparator;

class ConfrontaVotiPerValutazione implements Comparator<Voto> {

	@Override
	public int compare(Voto o1, Voto o2) {
		
		return -(o1.getVoto() - o2.getVoto());
	}

}
