package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class Simulazione {
	
	//PARAMETRI DELLA SIMULAZIONE
	private int turisti;
	private int giorni;
	private Graph<String, DefaultWeightedEdge> grafo;
	
	//PARAMETRI DA CALCOLARE
	private Map<String, Integer> mappaStatiTuristi; //STATO, NUM TURISTI NELLO STATO
	private Map<Integer, String> mappaTuristaStato; //ID TURISTA, STATO IN CUI SI TROVA

	public void initialize(Graph<String, DefaultWeightedEdge> g, String value, int t, int gg) {
		
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		this.grafo = g;
		
		this.giorni = gg;
		this.turisti = t;
		this.mappaStatiTuristi = new HashMap<>();
		this.mappaTuristaStato = new HashMap<>();
		
		for(String s: this.grafo.vertexSet()) {
			
			this.mappaStatiTuristi.put(s, 0);
		}
		
		int tur = 1;
		this.mappaStatiTuristi.replace(value, this.turisti);
		
		for(int i=0; i<this.turisti; i++) {
			
			this.mappaTuristaStato.put(tur, value);
			tur++;
		}
		
	}

	public void run() {
		
		for(int i=0; i<this.giorni; i++) {
			
			for(int t=1; t<=this.turisti; t++) {
				
				calcolaVolo(t);
				
			}
		}
	}


	private void calcolaVolo(int t) {
		
		String statoPartenza = this.mappaTuristaStato.get(t);
		List<StatoPeso> temp = new ArrayList<>(getStatiCollegati(statoPartenza));

		double totPeso = 0;
		for(StatoPeso s: temp) {
			totPeso+=s.getPeso();
		}
		
		for(int i=0; i<temp.size(); i++) {
			double random = Math.random();
			if(random<=(temp.get(i).getPeso()/totPeso)) {
				this.mappaTuristaStato.replace(t, temp.get(i).getStato());
				this.mappaStatiTuristi.replace(statoPartenza, this.mappaStatiTuristi.get(statoPartenza)-1);
				this.mappaStatiTuristi.replace(temp.get(i).getStato(), this.mappaStatiTuristi.get(temp.get(i).getStato())+1);
				break;
			}
		}
		
	}

	public Map<String, Integer> getRisultatoSimluazione() {
		// TODO Auto-generated method stub
		return this.mappaStatiTuristi;
	}
	
	public List<StatoPeso> getStatiCollegati(String stato) {
		
		List<String> listaStati = new ArrayList<>(Graphs.successorListOf(this.grafo, stato));
		List<StatoPeso> stati = new ArrayList<>();

		for (String s : listaStati) {

			double peso = this.grafo.getEdgeWeight(this.grafo.getEdge(stato, s));
			StatoPeso temp = new StatoPeso(s, peso);
			stati.add(temp);

		}

		return stati;
	}

}
