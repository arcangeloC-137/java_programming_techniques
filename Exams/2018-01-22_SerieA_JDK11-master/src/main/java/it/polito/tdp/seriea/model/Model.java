package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {
	
	private SerieADAO dao;
	private Map<Season, Integer> mappaAnnoPunteggio;
	private Map<Season, Integer> mappaTeam;
	private Map<String, Season> idMapSeason;
	private Graph<Season, DefaultWeightedEdge> grafo;
	private List<Season> listaStagioniTeam;
	
	public Model(){
		this.dao = new SerieADAO();
	}
	
	public List<AnnoPunteggio> getListaAnnoPunteggio(Team t) {
		
		
		this.mappaAnnoPunteggio = new HashMap<>();
		this.idMapSeason = new HashMap<>();
		this.mappaTeam = new HashMap<>();
		List<AnnoPunteggio> lista = new ArrayList<>();
		this.listaStagioniTeam = new ArrayList<>();
		
		List<Season> listaStagioni = new ArrayList<>(this.dao.listAllSeasons(idMapSeason));
		
		for(Season anno : listaStagioni) {
			this.mappaAnnoPunteggio.put(anno, 0);
		}
		
		this.dao.getRisultatiAnnate(t, mappaAnnoPunteggio, idMapSeason);
		
		for(Season s: this.mappaAnnoPunteggio.keySet()) {
			
			if(this.mappaAnnoPunteggio.get(s)>0) {
				lista.add(new AnnoPunteggio(s, this.mappaAnnoPunteggio.get(s)));
				this.mappaTeam.put(s, this.mappaAnnoPunteggio.get(s));
				this.listaStagioniTeam.add(s);
			}
		}
		
		Collections.sort(lista, new ComparatoreStagionePunteggio());
		
		return lista;
	}

	public List<Team> getAllTeams() {
		// TODO Auto-generated method stub
		return this.dao.listTeams();
	}

	public String getAnnataDoro(Team value) {
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, this.listaStagioniTeam);
		
		for(Season a: this.mappaTeam.keySet()) {
			
			Integer ptA = this.mappaTeam.get(a);
			
			for(Season b: this.mappaTeam.keySet()) {
				
				Integer ptB = this.mappaTeam.get(b);
				if(ptB>ptA) {
					
					Graphs.addEdge(this.grafo, a, b, ptB-ptA);
					
				}else if(ptA>ptB){
					
					Graphs.addEdge(this.grafo, b, a, ptA-ptB);
					
				}
			}
		}
		
		int best = 0;
		Season bestSeason = null;
		
		for(Season s: this.grafo.vertexSet()) {
			
			List<Season> archiUscenti = new ArrayList<>(Graphs.successorListOf(this.grafo, s));
			List<Season> archiEntranti = new ArrayList<>(Graphs.predecessorListOf(this.grafo, s));
			
			int uscenti = 0;
			int entranti = 0;
			
			for(Season e: archiEntranti) {
				entranti += this.grafo.getEdgeWeight(this.grafo.getEdge(e, s));
			}
			
			for(Season u: archiUscenti) {
				uscenti += this.grafo.getEdgeWeight(this.grafo.getEdge(s, u));
			}
			
			if(best<(entranti-uscenti)) {
				best = entranti-uscenti;
				bestSeason = s;
			}
		}
		
		String result = "Annata d'oro: "+bestSeason.getDescription()+"\nPunteggio: "+best+"\n";
		return result;
	}

	public String getCamminoVirtuoso(Team t) {
		
		Ricorsione ric = new Ricorsione();
		List<AnnoPunteggio> temp = new ArrayList<>(ric.getCamminoVirtuoso(this.grafo, getListaAnnoPunteggio(t)));
		
		String result = "Cammino virtoso trovato:\n";
		for(AnnoPunteggio a: temp) {
			result += "Stagione "+a.getStagione()+", punteggio: "+a.getPunteggio()+"\n";
		}
		return result;
	}

}
