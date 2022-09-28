package it.polito.tdp.seriea.model;

import java.util.Comparator;

public class ComparatoreStagionePunteggio implements Comparator< AnnoPunteggio> {

	@Override
	public int compare(AnnoPunteggio o1, AnnoPunteggio o2) {
		// TODO Auto-generated method stub
		return o1.getStagione().getSeason()-o2.getStagione().getSeason();
	}

}
