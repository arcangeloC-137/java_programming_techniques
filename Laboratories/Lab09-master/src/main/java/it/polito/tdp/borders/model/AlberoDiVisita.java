package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

public class AlberoDiVisita {
	
	private Graph<Country, DefaultEdge> grafo = new SimpleGraph<>(DefaultEdge.class);
	private Map<Country, Country> albero;
	
	public Map<Country, Country> alberoVisita(Country source, Graph<Country, DefaultEdge> graph){
		
		this.grafo = graph;
		albero = new HashMap<>();
		albero.put(source, null);
		
		GraphIterator<Country, DefaultEdge> bfv = new BreadthFirstIterator<>(grafo, source);
		
		bfv.addTraversalListener(new TraversalListener<Country, DefaultEdge>(){

			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
				
				DefaultEdge edge = e.getEdge();
				Country a = grafo.getEdgeSource(edge);
				Country b = grafo.getEdgeSource(edge);
				if(albero.containsKey(a) && !albero.containsKey(b)) {
					albero.put(b, a);
				}else {
					albero.put(a, b);
				}
				
			}

			@Override
			public void vertexTraversed(VertexTraversalEvent<Country> e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void vertexFinished(VertexTraversalEvent<Country> e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		while(bfv.hasNext()) {
			bfv.next();
		}
		return albero;
	}
}
