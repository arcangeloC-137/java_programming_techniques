package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {

	private Graph<Airport, DefaultWeightedEdge> grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	private Map<Integer, Airport> idMap;
	
	public Model() {
		
		this.idMap = new HashMap<>();
		
	}
	
	//Metodo generante il grafo
	public List<Adiacenze> genereateGraph(int x) {
		
		ExtFlightDelaysDAO dao = new ExtFlightDelaysDAO();
		dao.loadAllAirports(idMap);
		
		//Aggiungo gli archi al grafo
		List<Adiacenze> adiacenze = new ArrayList<>(dao.getEdges(idMap));
		List<Adiacenze> result = new ArrayList<>();
		
		for(Adiacenze a: adiacenze) {
			
			if(!grafo.containsEdge(a.getAirport1(), a.getAirport2())) { // || !grafo.containsEdge(a.getAirport2(), a.getAirport1())
				
				//Aggiungo vertici e peso degli archi al grafo
				if(a.getDistanceBetween()>=x) {
					Graphs.addEdgeWithVertices(grafo, a.getAirport1(), a.getAirport2(), a.getDistanceBetween());
					result.add(a);
				}
				
			}else {
				
				double nuovoPeso = ((grafo.getEdgeWeight(grafo.getEdge(a.getAirport2(), a.getAirport1()))+a.getDistanceBetween()))/2;
				if(nuovoPeso>x) {
					grafo.setEdgeWeight(grafo.getEdge(a.getAirport1(), a.getAirport2()), nuovoPeso);
				}
				
			}
		}
		
		return result;
		
	}
	
	
	public int nEdges(){
		return grafo.edgeSet().size();
	}
	
	public int nVertex() {
		return grafo.vertexSet().size();
	}
}
