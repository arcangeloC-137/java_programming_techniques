package it.polito.tdp.food.model;

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

import it.polito.tdp.food.db.FoodDAO;

public class Model {
	
	private List<Condiment> listaNodi;
	private Graph<Condiment, DefaultWeightedEdge> grafo;
	private FoodDAO dao;
	private Map <Integer, Condiment> idMap;
	
	public void creaGrafo(double calories) {
		
		this.dao = new FoodDAO();
		this.idMap = new HashMap<>();
		this.dao.listAllCondiment(this.idMap);
		
		this.listaNodi = new ArrayList<>(this.dao.listCondimentByCalories(calories));
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, this.listaNodi);
		
		this.dao.addEdges(this.grafo, this.idMap);
		
	}
	
	public int getNumVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNumArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public String getListaIngredienti() {
		
		String result = "Lista Ingredienti:\n";
		List<CondimentAndCalories> lista = new ArrayList<>();
		for(Condiment c: this.grafo.vertexSet()) {
			
			int cibi = 0;
			
			List<Condiment> temp = new ArrayList<>(Graphs.neighborListOf(this.grafo, c));
			for(Condiment c1: temp) {
				
				cibi += cibi +this.grafo.getEdgeWeight(this.grafo.getEdge(c, c1));
				
			}
			
			lista.add(new CondimentAndCalories(c, c.getCondiment_calories(), cibi));
			
		}
		
		Collections.sort(lista, new ComparatoreCalorieCondimenti());
		
		for(CondimentAndCalories c: lista) {
			result += c.getCondiment().getDisplay_name()+
					"\n"+c.getCalories()+"calorie, "+c.getNumCibiAdiacenza()+" cibi corrispondenti\n";
		}
		
		return result;
	}

	public Set<Condiment> getIngredienti() {
		
		return this.grafo.vertexSet();
	}
	
}
