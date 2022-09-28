package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.List;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();

		System.out.println("TestModel -- TODO");
		model.generateGraph(2000);
		List<Country> listaVicini = new ArrayList<>(model.trovaVicini(new Country("USA", 2, "United States of America")));
		
		for(Country c: listaVicini) {
			System.out.println(c.getStateName()+"\n");
		}
		
//		System.out.println("Creo il grafo relativo al 2000");
//		model.createGraph(2000);
		
//		List<Country> countries = model.getCountries();
//		System.out.format("Trovate %d nazioni\n", countries.size());

//		System.out.format("Numero componenti connesse: %d\n", model.getNumberOfConnectedComponents());
		
//		Map<Country, Integer> stats = model.getCountryCounts();
//		for (Country country : stats.keySet())
//			System.out.format("%s %d\n", country, stats.get(country));		
		
	}

}
