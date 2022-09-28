package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.borders.model.Event.EventType;

public class Simulator {

	//Genero una coda degli eventi
	private PriorityQueue<Event> queue = new PriorityQueue<>();
	
	//1. Parametri di simulazione
	private int T = 1;
	private int migranti = 1000;
	
	//2. Modello del mondo
	private Graph<Country, DefaultEdge> graph = new SimpleWeightedGraph<>(DefaultEdge.class);
	private List<CountryAndNumber> paesi = new ArrayList<>(); 
	private CountryAndNumber paeseOrigine;
	
	public void setModelloMondo(Graph<Country, DefaultEdge> grafo, List<CountryAndNumber> list) {
		
		this.paesi = list;
		this.graph = grafo;
	}

	public void setPaeseOrigine(CountryAndNumber paeseOrigine) {
		this.paeseOrigine = paeseOrigine;
	}

	//3.Valori da calcolare:
	private List<CountryAndNumber> listaPassiSimulati = new ArrayList<>();
	private Map<Country, Integer> mappaPaesiSimulati = new HashMap<>();
	
	public void run() {
		
		this.queue.clear();
		Event primo = new Event(EventType.EMIGRAZIONE, this.T, this.migranti, this.paeseOrigine.getCountry());
		this.queue.add(primo);
		//mappaPaesiSimulati.put(primo.getPaese(), primo.getNumPersone());
		this.T++;
		
		/*Event secondo = new Event(EventType.EMIGRAZIONE, this.T, (this.migranti-this.migranti/2), this.paeseOrigine.getCountry());
		this.queue.add(secondo);
		
		int count = 0;
		int migTemp = (int) Math.ceil( (this.migranti-this.migranti/2)/this.paeseOrigine.getNumber());
		
		List<Country> listaPaesiVicini = new ArrayList<>();
		listaPaesiVicini = Graphs.neighborListOf(this.graph, this.paeseOrigine.getCountry());
		
		do {
			Event e = new Event(EventType.EMIGRAZIONE, this.T, migTemp, listaPaesiVicini.get(count));
			this.queue.add(e);
			count++;
			mappaPaesiSimulati.put(e.getPaese(), e.getNumPersone());
	
			
		}while(count<this.paeseOrigine.getNumber());*/
		
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
		}
		
		for(Country c: this.mappaPaesiSimulati.keySet()) {
			System.out.println("Paese: "+c.getStateName()+", abitanti: "+this.mappaPaesiSimulati.get(c));
		}
		
	}

	private void processEvent(Event e) {
		
		/*
		 * Se il numero dei NON-stanziali è inferiore al numero di paesi confinanti (paeseOrigine.getNumber()),
		 * allora non si muove nessuno
		 */
		
		int notStanziali = (int) Math.ceil(e.getNumPersone()/2);
		int numeroPaesiVicini = 0;
		for(CountryAndNumber c: this.paesi) {
			if(c.getCountry().equals(e.getPaese())) {
				numeroPaesiVicini = c.getNumber();
			}
		}
		
		if(notStanziali >= numeroPaesiVicini) {
			
			if(this.mappaPaesiSimulati.containsKey(e.getPaese())) {
				int persTemp = this.mappaPaesiSimulati.get(e.getPaese());
				mappaPaesiSimulati.put(e.getPaese(), persTemp+e.getNumPersone()-notStanziali);
			}else {
				mappaPaesiSimulati.put(e.getPaese(), e.getNumPersone()-notStanziali);
			}
			
			
			List<Country> listaPaesiVicini = new ArrayList<>();
			listaPaesiVicini = Graphs.neighborListOf(this.graph, e.getPaese());
			int migTemp = (int) Math.ceil( notStanziali/numeroPaesiVicini);
			
			for(Country paese: listaPaesiVicini) {
				
				Event nuovo = new Event(EventType.EMIGRAZIONE, e.getTempo()+1, migTemp, paese);
				this.queue.add(nuovo);
				if(this.mappaPaesiSimulati.containsKey(nuovo.getPaese())) {
					int personePrec = this.mappaPaesiSimulati.get(nuovo.getPaese());
					mappaPaesiSimulati.put(nuovo.getPaese(), migTemp+personePrec);
				}else {
					mappaPaesiSimulati.put(nuovo.getPaese(), migTemp);
				}
				//mappaPaesiSimulati.put(nuovo.getPaese(), migTemp);
			}
			
		}
		
		
	}

	public Map<Country, Integer> getMappaSimulazione() {
		
		return mappaPaesiSimulati;
		
	}
	
}
