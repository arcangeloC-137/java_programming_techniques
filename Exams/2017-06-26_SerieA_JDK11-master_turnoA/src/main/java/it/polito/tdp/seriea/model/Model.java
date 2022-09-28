package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {

	private Graph<Team, DefaultWeightedEdge> grafo;
	private SerieADAO dao;
	private Map<String, Team> idMap;

	public void creaGrafo() {
		
		this.dao = new SerieADAO();
		this.idMap = new HashMap<>();
		
		this.dao.listTeams(this.idMap);
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.dao.riempiGrafo(this.grafo, this.idMap);
	}
	
	

	public int getNvertici() {
		// TODO Auto-generated method stub
		return this.grafo.vertexSet().size();
	}

	 public int getNarchi() {
		// TODO Auto-generated method stub
		return this.grafo.edgeSet().size();
	}

	public Set<Team> getSquadre() {
		// TODO Auto-generated method stub
		return this.grafo.vertexSet();
	}



	public List<SquadrePartiteGiocate> getConnessioniSquadra(Team value) {
		
		List<Team> temp = new ArrayList<>(Graphs.neighborListOf(this.grafo, value));
		List<SquadrePartiteGiocate> result = new ArrayList<>();
		
		for(Team t: temp) {
			result.add(new SquadrePartiteGiocate(value, t, this.grafo.getEdgeWeight(this.grafo.getEdge(value, t))));
		}
		
		Collections.sort(result, new ComparatoreSquadrePartite());
		return result;
	}



	public List<Season> getStagioni(){
		
		// TODO Auto-generated method stub
		return this.dao.listSeasons();
	}



	public String simulaTifosi(Season value) {
		
		Simulatore sim = new Simulatore();
		List<Match> listaIncontri = new ArrayList<>();
		this.dao.getListaIncontriStagione(value, listaIncontri, idMap);
		
		
		List<Team> listaSquadreCampionato = new ArrayList<>();
		this.dao.getSquadreCampionato(value, listaSquadreCampionato, this.idMap);
		
		sim.initialize(listaIncontri, listaSquadreCampionato);
		sim.run();
		Map<Team, Integer> mappaSquadraTifosi = new HashMap<>(sim.getSquadrePuntiTifosi());
		
		String result = "Risultati campionato "+value.getDescription()+":\n";
		for(Team t: mappaSquadraTifosi.keySet()) {
			result += t.getTeam()+", "+t.getPunteggio()+" punti, "+mappaSquadraTifosi.get(t)+" tifosi\n";
		}
		return result;
	}

}
