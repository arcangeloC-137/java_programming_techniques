package it.polito.tdp.anagrammi.model;

import java.util.ArrayList;
import java.util.List;

public class TestRicerca {

	public static void main(String[] args) {
		
		Ricerca ric = new Ricerca();
		
		List<String> anagramma = new ArrayList<>(ric.anagrammi("mamma"));
		System.out.println(anagramma);
	}
}
