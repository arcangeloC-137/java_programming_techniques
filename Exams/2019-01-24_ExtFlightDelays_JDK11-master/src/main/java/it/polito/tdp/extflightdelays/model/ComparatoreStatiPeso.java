package it.polito.tdp.extflightdelays.model;

import java.util.Comparator;

public class ComparatoreStatiPeso implements Comparator<StatoPeso> {

	@Override
	public int compare(StatoPeso o1, StatoPeso o2) {
		// TODO Auto-generated method stub
		return -o1.getPeso().compareTo(o2.getPeso());
	}

}
