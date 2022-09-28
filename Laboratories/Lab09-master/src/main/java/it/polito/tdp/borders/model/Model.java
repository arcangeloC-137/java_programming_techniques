package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private Graph<Country, DefaultEdge> grafo = new SimpleGraph<>(DefaultEdge.class);
	private Map<Integer, Country> idMap;
	private BordersDAO dao;

	public Model() {
		this.idMap = new HashMap<>();
		dao = new BordersDAO();
		dao.loadAllCountries(idMap);
	}
	
	public Collection<Country> generateGraph(int anno) {
		
		//Carcico tutti i possibili nodi nella idMap, e la passo al dao per cercare i confini precedenti l'anno		
		List<Border> confini = new ArrayList<>(dao.getCountryPairs(anno, idMap));
		List<Country> temp = new ArrayList<>();
		
		for(Border b: confini) {
			if(!grafo.containsEdge(b.getStateOne(), b.getStateTwo())) {
				Graphs.addEdgeWithVertices(grafo, b.getStateOne(), b.getStateTwo());
			}
			if(!temp.contains(b.getStateOne())) {
				temp.add(b.getStateOne());
			}
		}
		return temp;
	}
	
	public String trovaStatiConfinanti(){
		
		String result = "";
		for(Country c: idMap.values()) {
			
			if(grafo.containsVertex(c)) {
				
				List<Country> vicini = new ArrayList<>(Graphs.neighborListOf(grafo, c));
				result+=c.getStateName().toUpperCase()+", stati confinanti ("+vicini.size()+"):\n";
				
				for(Country v: vicini) {
					result+=v.getStateName()+"\n";
				}
			}
			
		}
		
		return result;
	}
	
	public List<Country> trovaVicini(Country stato) {
		
		Recursive ricerca = new Recursive();
		List<Country> temp = new ArrayList<>(ricerca.getListaVicini(grafo, stato));
		return temp;
		
	}
	
	public List<Country> visitaInProfondita(Country source) {
		
		GraphIterator<Country, DefaultEdge> dfv = new DepthFirstIterator<>(grafo, source);
	    List<Country> visit = new ArrayList<>();
	    
	    while(dfv.hasNext()) {
	    	visit.add(dfv.next());
	    }
	    
	    return visit;
	}
	
    public List<Country> visitaInAmpiezza(Country source) {
		
		GraphIterator<Country, DefaultEdge> bfv = new BreadthFirstIterator<>(grafo, source);
	    List<Country> visit = new ArrayList<>();
	    
	    while(bfv.hasNext()) {
	    	visit.add(bfv.next());
	    }
	    
	    return visit;
	}
	
	public void alberoDiVisita(Country stato) {
		
		AlberoDiVisita albero = new AlberoDiVisita();
		Map<Country, Country> result = new HashMap<>(albero.alberoVisita(stato, grafo));
		
	}

	public int numeroComponentiGrafo() {
		
		ConnectivityInspector<Country, DefaultEdge> ci = new ConnectivityInspector<>(grafo);
		List<Set<Country>> result = ci.connectedSets();
		return result.size();
	}
	
	public int nVertex() {
		return grafo.vertexSet().size();
	}
	public int nEdges() {
		return grafo.edgeSet().size();
	}

	public Collection<Country> getStatiDelGrafo() {
		return this.grafo.vertexSet();
	}


}
