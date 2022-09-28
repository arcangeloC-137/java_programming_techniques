package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	private EventsDao dao;
	private Graph<String, DefaultWeightedEdge> grafo;
	private List<String> best;
	
	public Model() {
		this.dao = new EventsDao();
	}
	
	public List<String> getCategorie(){
		return this.dao.getCategorie();
	}
	
	public List<Integer> getMesi(){
		return this.dao.getMesi();
	}
	
	//CREAZIONE DEL GRAFO
	public void generateGraph(String categoryCrime, int mese) {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//MI FACCIO DARE LE ADIACENZE DAL DAO
		List<Adiacenze> adiacenze = this.dao.getAdiacenze(categoryCrime, mese);
		
		for(Adiacenze a: adiacenze) {
			
			if(!this.grafo.containsVertex(a.getReato1())) {
				this.grafo.addVertex(a.getReato1());
			}
			
			if(!this.grafo.containsVertex(a.getReato2())) {
				this.grafo.addVertex(a.getReato2());
			}
			
			if(this.grafo.getEdge(a.getReato1(), a.getReato2())==null) {
				Graphs.addEdgeWithVertices(grafo, a.getReato1(), a.getReato2(), a.getPeso());
			}
		}
		
	}

	public int getNvertex() {
		return this.grafo.vertexSet().size();
	}
	public int getNedges() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Arco> getArchi(){
		
		double pesoMedio = 0.0;
		for(DefaultWeightedEdge e : this.grafo.edgeSet()) {
			pesoMedio += this.grafo.getEdgeWeight(e);
		}
		pesoMedio = pesoMedio/this.grafo.edgeSet().size();
		
		List<Arco> archi = new ArrayList<>();
		for(DefaultWeightedEdge e : this.grafo.edgeSet()) {
			if(this.grafo.getEdgeWeight(e)>pesoMedio) {
				archi.add(new Arco(this.grafo.getEdgeSource(e), this.grafo.getEdgeTarget(e), this.grafo.getEdgeWeight(e)));
			}
		}
		
		Collections.sort(archi);
		
		return archi;
	}
	
	public List<String> trovaPercorsi(String sorgente, String destinazione){
		
		List<String> parziale = new ArrayList<>();
		this.best = new ArrayList<>();
		
		parziale.add(sorgente);
		
		cerca(destinazione, parziale);
		
		return this.best;
		
	}

	private void cerca(String destinazione, List<String> parziale) {
		
		//CASO TERMINALE: l'ultimo vertice in parziale è uguale al nodo destinazione
		if(parziale.get(parziale.size()-1).equals(destinazione)) {
			
			if(parziale.size()>best.size()) {
				this.best = new ArrayList<>(parziale);
			}
			return;
		}
		
		//Scorro i vicini dell'ultimo vertice inserito in parziale
		//NB: non voglio cilci, controllo che non abbia visitato lo stesso nodo più volte
		for(String vicino: Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1))) {
			
			//NB: non voglio cilci, controllo che non abbia visitato lo stesso nodo più volte
			if(!parziale.contains(vicino)) {
				parziale.add(vicino);
				cerca(destinazione, parziale);
				parziale.remove(parziale.size()-1);
			}
			
		}
	}


	
}
