package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	private SimpleWeightedGraph<Airport, DefaultWeightedEdge> grafo;
	private Map<Integer, Airport> idMap;
	private ExtFlightDelaysDAO dao;
	
	//Mappa che modella le relazioni padre figlio nella modellazione della visita
	private Map<Airport, Airport> visita = new HashMap<>();
	
	public Model() {
		idMap = new HashMap<>();
		dao = new ExtFlightDelaysDAO();
		this.dao.loadAllAirports(idMap);
	}
	
	public void creaGrafo(int x) {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//Aggiungo i vertici
		for(Airport a: idMap.values()) {
			if(dao.getAirlinesNumber(a)>x) {
				//Inserisco l'aeroporto come vertice
				this.grafo.addVertex(a);
			}
			
			for(Rotta r: dao.getRotte(idMap)) {
				
				if(this.grafo.containsVertex(r.getA1()) && this.grafo.containsVertex(r.getA2())) {
					DefaultWeightedEdge e = this.grafo.getEdge(r.getA1(), r.getA2());
					
					if(e==null) {
						Graphs.addEdgeWithVertices(this.grafo, r.getA1(),  r.getA2(), r.getPeso());
					}else {
						double pesoVecchio = this.grafo.getEdgeWeight(e);
						double pesoNuovo = pesoVecchio+r.getPeso();
						
						this.grafo.setEdgeWeight(e, pesoNuovo);
					}
				}
				
			}
		}
	}
	
	public int nVertex() {
		return this.grafo.vertexSet().size();
	}
	
	public int nEdge() {
		return this.grafo.edgeSet().size();
	}
	
	public Collection<Airport> getAeroporti(){
		return this.grafo.vertexSet();
	}
	
	public List<Airport> trovaPercorso(Airport a1, Airport a2){
		
		List<Airport> percorso = new ArrayList<>();
		
		//provo un visita in ampiezza
		BreadthFirstIterator<Airport, DefaultWeightedEdge> it = new BreadthFirstIterator<>(this.grafo, a1);
		
		/*
		 * dobbiamo agganciare un traversal listener all'iteratore, per essere notificati ogni qual volta
		 * attraversiamo un arco
		 */
		
		/**
		 * Salviamo la radice dell'albero
		 */
		
		visita.put(a1, null);
		
		it.addTraversalListener(new TraversalListener<Airport, DefaultWeightedEdge>(){

			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultWeightedEdge> e) {
				/*
				 * L'unico metodo utile ?? questo, dove salviamo la relazione
				 * padre/figlio tra nodo sorgente e destinazione nell'albero di visite, 
				 * che per noi ?? una mappa
				 */
				Airport sorgente = grafo.getEdgeSource(e.getEdge());
				Airport destinazione = grafo.getEdgeTarget(e.getEdge());
				
				//Mi chiedo se la mia visita contenga o meno la destinazione, ma contiene il nodo sorgente
				if(!visita.containsKey(destinazione) && visita.containsKey(sorgente)) {
					/*
					 * Se questo ?? vero, nel nostro albero di visita abbiamo il nodo 
					 * sorgente e possiamo modellare la relazione padre e flglio 
					 * da sorgente a destinazione.
					 * AKA: LA NOSTRA DESTINAZIONE SI RAGGIUNGE DA SORGENTE
					 */
					visita.put(destinazione, sorgente);
					
					/*
					 * Essendo, tuttavia, un grafo non orientato, per noi 
					 * sorgente e destinazione sono intercamnbiabili. Occorre trattare il caso opposto
					 */
				}
				
				if(!visita.containsKey(sorgente) && visita.containsKey(destinazione)) {
					visita.put(sorgente, destinazione);
				}
				
			}

			@Override
			public void vertexTraversed(VertexTraversalEvent<Airport> e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void vertexFinished(VertexTraversalEvent<Airport> e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		while(it.hasNext()) {
			it.next();
		}
		
		if(!visita.containsKey(a1) || !visita.containsKey(a2)) {
			//i due aeroporti NON sono collegati
			return null;
		}
		
		Airport step = a2;
		while(!step.equals(a1)) {
			//risalgo l'albero di visita finch?? non ritrovo il nodo di partenza
			percorso.add(step);
			step = visita.get(step);
		}
		percorso.add(a1); 
		return percorso;
	}
	
}
