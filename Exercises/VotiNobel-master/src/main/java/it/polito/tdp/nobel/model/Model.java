package it.polito.tdp.nobel.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {

	private List<Esame> esami;
	private double bestMedia = 0;
	private Set<Esame> bestSoluzione = null;
	
	public Model() {
		EsameDAO dao = new EsameDAO();
		this.esami = dao.getTuttiEsami();
	}
	
	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		
		Set<Esame> parziale = new HashSet<>();
		cerca2(parziale, 0, numeroCrediti);
		return bestSoluzione;
	}

	/**
	 * Approccio 1
	 * complessita: 2^N
	 * @param parziale
	 * @param L
	 * @param m
	 */
	public void cerca( Set<Esame> parziale, int L, int m) {
		/**
		 * Casi terminali
		 */
		int crediti = sommaCrediti(parziale);
		if(crediti>m) {
			return;
		}
		
		if(crediti == m) {
			double media = calcolaMedia(parziale);
			if(media > bestMedia) {
				bestSoluzione = new HashSet<>(parziale);
				bestMedia = media;
			}
		}
		
		/**
		 * Se arrivo fin qui, crediti < m
		 */
		
		if(L == esami.size()) {
			return;
		}
		
		/*
		 * Generiamo i sotto problemi
		 * esami(L) e' da aggiungere o no?
		 * Provo entrambe le strade
		 */
		
		//PROVO AD AGGIUNGERLO
		parziale.add(esami.get(L));
		cerca(parziale, L+1, m);
		//Backtracking
		parziale.remove(esami.get(L));
		
		//PROVO A NON AGGIUNGERLO
		cerca(parziale, L+1, m);
	}
	
	/**
	 * Approccio 2
	 * complessità: N!
	 * @param parziale
	 * @param L
	 * @param m
	 */
	private void cerca2( Set<Esame> parziale, int L, int m) {
		
		/**
		 * Casi terminali
		 */
		int crediti = sommaCrediti(parziale);
		if(crediti>m) {
			return;
		}
		
		if(crediti == m) {
			double media = calcolaMedia(parziale);
			if(media > bestMedia) {
				bestSoluzione = new HashSet<>(parziale);
				bestMedia = media;
			}
		}
		
		/**
		 * Se arrivo fin qui, crediti < m
		 */
		if(L == esami.size()) {
			return;
		}
		
		/**
		 * Genero i sotto problemi
		 */
		for(Esame e: esami) {
			if (!parziale.contains(e)) {
				parziale.add(e);
				cerca2(parziale, L + 1, m);
				parziale.remove(e);
			}
		}
    }

	public double calcolaMedia(Set<Esame> parziale) {
		int crediti = 0;
		int somma = 0;
		for(Esame e: parziale) {
			crediti += e.getCrediti();
			somma += e.getVoto()*e.getCrediti();
		}
		return somma/crediti;
	}

	private int sommaCrediti(Set<Esame> parziale) {
		int somma=0;
		for(Esame e: parziale) {
			somma += e.getCrediti();
		}
		return somma;
	}
}
