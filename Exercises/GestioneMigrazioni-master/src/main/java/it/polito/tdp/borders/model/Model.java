package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private Graph<Country, DefaultEdge> graph ;
	private Map<Integer,Country> countriesMap ;
	
	public Model() {
		this.countriesMap = new HashMap<>() ;

	}
	
	public void creaGrafo(int anno) {
		
		this.graph = new SimpleGraph<>(DefaultEdge.class) ;

		BordersDAO dao = new BordersDAO() ;
		
		//vertici
		dao.getCountriesFromYear(anno,this.countriesMap) ;
		Graphs.addAllVertices(graph, this.countriesMap.values()) ;
		
		// archi
		List<Adiacenza> archi = dao.getCoppieAdiacenti(anno) ;
		for( Adiacenza c: archi) {
			graph.addEdge(this.countriesMap.get(c.getState1no()), 
					this.countriesMap.get(c.getState2no())) ;
			
		}
	}
	
	public List<CountryAndNumber> getCountryAndNumber() {
		List<CountryAndNumber> list = new ArrayList<>() ;
		
		for(Country c: graph.vertexSet()) {
			list.add(new CountryAndNumber(c, graph.degreeOf(c))) ;
		}
		Collections.sort(list , new ComparatoreNazioniNome());
		return list ;
	}
	
	public List<CountryAndNumber> simulaMigrazione(CountryAndNumber paeseOrigine) {
		
		Simulator sim = new Simulator();
		Map<Country, Integer> mappaPaesiSimulati = new HashMap<>();
		List<CountryAndNumber> paesiSim = new ArrayList<>() ;
		sim.setPaeseOrigine(paeseOrigine);
		sim.setModelloMondo(this.graph, this.getCountryAndNumber());
		sim.run();
		
		mappaPaesiSimulati = sim.getMappaSimulazione();
		for(Country c: mappaPaesiSimulati.keySet()) {
			paesiSim.add(new CountryAndNumber(c, mappaPaesiSimulati.get(c)));
		}
		
		Collections.sort(paesiSim , new ComparatoreNazioniNome());
		return paesiSim;
	}
	
	public Graph<Country, DefaultEdge> getGrafo() {
		return this.graph;
	}
	

}
