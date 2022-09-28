package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class Ricorsione {
	
	private List<AnnoPunteggio> listaAnnoPunteggio;
	private List<AnnoPunteggio> bestCammino;
	private Graph<Season, DefaultWeightedEdge> grafo;
	private int bestPunteggio;

	public List<AnnoPunteggio> getCamminoVirtuoso(Graph<Season, DefaultWeightedEdge> g, List<AnnoPunteggio> list) {
		
		this.listaAnnoPunteggio = new ArrayList<>(list);
		this.bestPunteggio = 0;
		this.bestCammino = new ArrayList<>();
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		this.grafo = g;
		
		List<AnnoPunteggio> parziale = new ArrayList<>();
		cerca(parziale, 0, 0);
		
		return this.bestCammino;
	}

	private void cerca(List<AnnoPunteggio> parziale, int posizionePartenza, int punteggio) {
		
		if(this.bestPunteggio<punteggio) {
			this.bestPunteggio = punteggio;
			this.bestCammino = new ArrayList<>(parziale);
		}
		
		if(posizionePartenza == this.listaAnnoPunteggio.size()) {
			return;
		}
		
		AnnoPunteggio temp = this.listaAnnoPunteggio.get(posizionePartenza);
		
		if(posizionePartenza==0) {
			parziale.add(temp);
			cerca(parziale, posizionePartenza+1, punteggio+this.listaAnnoPunteggio.get(posizionePartenza).getPunteggio());
			
		}else {
			
			if(temp.getPunteggio()>parziale.get(parziale.size()-1).getPunteggio()) {
				parziale.add(temp);
				cerca(parziale, posizionePartenza+1, punteggio+this.listaAnnoPunteggio.get(posizionePartenza).getPunteggio());
			}else {
				parziale = new ArrayList<>();
				parziale.add(temp);
				cerca(parziale, posizionePartenza+1, temp.getPunteggio());
			}
			
		}
		
	}

}
