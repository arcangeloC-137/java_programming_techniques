package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;
import javafx.scene.control.TextField;

public class Model {

	private Graph<String, DefaultWeightedEdge> grafo;
	private ExtFlightDelaysDAO dao;
	
	public void generaGrafo() {
		
		this.dao = new ExtFlightDelaysDAO();
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, this.dao.loadAllUSAStates());
		this.dao.addAllEdges(this.grafo);
	}
	
	public String getStatiCollegati(String stato) {
		
		String result = "Stati collegati a "+stato+" con un arco diretto:\n";
		
		List<String> listaStati = new ArrayList<>(Graphs.successorListOf(this.grafo, stato));
		List<StatoPeso> statiOrdinati = new ArrayList<>();
		
		for(String s: listaStati) {
			
			double peso = this.grafo.getEdgeWeight(this.grafo.getEdge(stato, s));
			StatoPeso temp = new StatoPeso(s, peso);
			statiOrdinati.add(temp);
			
		}
		
		Collections.sort(statiOrdinati, new ComparatoreStatiPeso());
		for(StatoPeso s: statiOrdinati) {
			result+=s.getStato()+" "+s.getPeso()+"\n";
		}
		
		return result;
	}
	
	public int getNvertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNarchi() {
		return this.grafo.edgeSet().size();
	}

	public Set<String> getStati() {
		// TODO Auto-generated method stub
		return this.grafo.vertexSet();
	}

	public String simulazioneTuristi(String value, int turisti, int giorni) {
		
		Simulazione sim = new Simulazione();
		sim.initialize(this.grafo, value, turisti, giorni);
		sim.run();
		
		Map<String, Integer> mappaSimulazione = new HashMap<>(sim.getRisultatoSimluazione());
		String result = "Turisti presenti in ciscuno stato al termine dei "+giorni+" giorni:\n";
		
		for(String state: mappaSimulazione.keySet()) {
			
			result+="Stato: "+state+", #turisti: "+mappaSimulazione.get(state)+"\n";
		}
		
		return result;
	}
}
