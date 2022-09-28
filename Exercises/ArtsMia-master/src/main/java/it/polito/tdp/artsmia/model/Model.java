package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {

	private Graph<ArtObject, DefaultWeightedEdge> grafo; //Grafo semplice, non orientato, pesato 
	//Ci interessa creare una sola volta lo stesso oggetto
	private Map<Integer, ArtObject> idMap;
	
	public Model() {
		
		this.idMap = new HashMap<>();
	}
	
	public void creaGrafo() {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		//Chiediamo al dao di riempire la mappa con oggetti presenti una sola volta
		ArtsmiaDAO dao = new ArtsmiaDAO();
		
		//Aggiungere i vertici (corrispondenti agli Objects del DB)
		dao.listObjects(idMap); //Ora la mappa contiene tutti gli oggetti Objects
		Graphs.addAllVertices(this.grafo, this.idMap.values());
		
		//Aggiungere gli archi tra i vertici
		
		//APPROCCIO 1
		/**
		 * Doppio ciclo for sui vertici. Dati due vertici controllo se siano 
		 * collegati. Metodo molto laborioso, utile sono in alcuni casi. 
		 * NOT THE BEST
		 * 
		 * Calcolando, servirebber ben 68 giorni per temrinare la ricerca
		 */
		
		/*
		 * ATTENZIONE: con il doppio ciclo for, si avrà sia l'oggetto (a1, a2) che (a2,
		 * a1) Per evitare di inserire due volte la stessa cosa, posso controllare a
		 * priori l'esistenza dell'arco, non essendo il grafo orientato. L'arco va bene
		 * sia in un senso che nell'altro, ergo se già presente evito di reinserirlo
		 */
		
		/*for (ArtObject a1 : this.grafo.vertexSet()) {

			for (ArtObject a2 : this.grafo.vertexSet()) { // devo collegare a1 ad a2??

				// Restituisce 0 se a1 non collegato ad a2
				int peso = dao.getPeso(a1, a2);

				if (peso > 0) {
					// Condizione di if che controlla se l'arco sia già presente
					if (!this.grafo.containsEdge(a1, a2)) {
						Graphs.addEdge(this.grafo, a1, a2, peso);
					}

				}

			}
		}*/
		//System.out.println(String.format("Grafo creato! # vertici %d, # Archi %d", this.grafo.vertexSet().size(), this.grafo.edgeSet().size()));
        
		// APPROCCIO 2
		/**
		 * Si potrebbe cambiare la query facendoci restituire tutti gli Objects
		 * corrispondenti ad uno dato in input. Questo abbasserebbe di molto il tempo,
		 * ma sarebbero comunque 35 minuti circa. NON BUONO
		 * 
		 * QUERY:
		 * SELECT eo2.object_id, COUNT(*) FROM exhibition_objects AS eo1,
		 * exhibition_objects AS eo2 WHERE eo1.exhibition_id = eo2.exhibition_id AND
		 * eo1.object_id = 8485 AND eo2.object_id != eo1.object_id GROUP BY
		 * eo2.object_id
		 */
		
		//APPROCCIO 3
		for(Adiacenza a: dao.getAdiacenze()) {
			if(a.getPeso()>0) {
				//abbiamo escluso le coppie a specchio mediante AND id1>id2 nella query
				Graphs.addEdge(this.grafo, idMap.get(a.getObj1()), idMap.get(a.getObj2()), a.getPeso());
			}
		}
		System.out.println(String.format("Grafo creato! # vertici %d, # Archi %d", this.grafo.vertexSet().size(), this.grafo.edgeSet().size()));
	}
	
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	
	public int nVertici(){
		return this.grafo.vertexSet().size();}
	
	
}
