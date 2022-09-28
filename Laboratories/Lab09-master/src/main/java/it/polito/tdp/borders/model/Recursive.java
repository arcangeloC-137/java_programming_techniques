package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class Recursive {

	//CLASSE CONTENENTE L'ALGORITMO RICORSIVO
	private Graph<Country, DefaultEdge> grafo = new SimpleGraph<>(DefaultEdge.class);
	
	/**
	 * 
	 * @param grafo (Grafo creato a partire da un anno specificato)
	 * @param statoPartenza (punto di partenza per la ricerca del cammino)
	 * @return Lista di stati raggiungibili via terra dallo stato passato come parametro
	 */
	public List<Country> getListaVicini(Graph<Country, DefaultEdge> graph, Country statoPartenza){
		
		grafo = graph;
		List<Country> parziale = new ArrayList<>();
		parziale.add(statoPartenza);
		cerca(parziale, statoPartenza);
		
		return parziale;
	}
	
	public void cerca(List<Country> parziale, Country paeseVisitato){
	
		
		/*
		 * CASO GENERALE: dal paese in cui mi trovo prelevo i suoi vicini,
		 *                controllo se siano già stati inseriti e, nel caso
		 *                rimando la ricorsione
		 */
		List<Country> adiacenti = new ArrayList<>(Graphs.neighborListOf(grafo, paeseVisitato));
		for(Country c: adiacenti) {
			
			//Controllo che il nodo adiacente non sia già nella lista parziale
			if(!parziale.contains(c)) {
				parziale.add(c);
				cerca(parziale, c);
			}
		}
		
	}


	
}
