package it.polito.tdp.seriea.model;

import java.util.Comparator;

public class ComparatoreSquadrePartite implements Comparator< SquadrePartiteGiocate> {

	@Override
	public int compare(SquadrePartiteGiocate o1, SquadrePartiteGiocate o2) {
		// TODO Auto-generated method stub
		return -(o1.getPartiteGiocate().compareTo(o2.getPartiteGiocate()));
	}

}
