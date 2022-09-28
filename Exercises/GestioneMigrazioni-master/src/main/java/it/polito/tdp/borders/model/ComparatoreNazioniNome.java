package it.polito.tdp.borders.model;

import java.util.Comparator;

public class ComparatoreNazioniNome implements Comparator<CountryAndNumber> {

	@Override
	public int compare(CountryAndNumber o1, CountryAndNumber o2) {
		
		return o1.getCountry().getStateName().compareTo(o2.getCountry().getStateName());
	}

}
