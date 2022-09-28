package it.polito.tdp.anagrammi.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import it.polito.anagrammi.dao.DizionarioDAO;

public class Ricerca {

	private List<String> soluzione;
	private DizionarioDAO dao;
	
	public Ricerca() {
		
		this.dao = new DizionarioDAO();
	}
	
	/**
	 * Genera tutti gli anagrammi della parola specificata in ingresso.
	 * @param parola parola da anagrammare
	 * @param elenco di tutti gli anagrammi della parola data
	 */
	public List<String> anagrammi(String parola){
		
		if(!this.dao.getParolaDaDizionario(parola)) {
			return null;
		}
		this.soluzione = new ArrayList<>();
		List<Character>  disponibili = new ArrayList<>();
		parola.toUpperCase();
		
		for(int i=0; i<parola.length(); i++) {
			disponibili.add(parola.charAt(i));
		}
		//CASO INIZIALE
		/**
		 * avvio la ricorsione
		 */
		cerca("", 0, disponibili);
		
		return this.soluzione;
	}
	
	/**
	 * Funzione ricorsiva, chiamabile solo in questa classe
	 * Procedura ricorsiva per il calcolo dell'anagramma
	 * 
	 * @param parziale (parte iniziale della parola costruita fin'ora (eat ---> at ---> t, per esempio)
	 * @param livello (parziale.lenght())
	 * @param lettereDisponibili (lettere ancora inseribili nella parola, non ANCORA utilizzate), utilizzo una LIST e non un SET perché in set
	 *        cancellerebbe eventuali lettere ripetute
	 */
	private void cerca(String parziale, int livello, List<Character> lettereDisponibili) { //Il livello in questo caso è superfluo, in quanto si tratta di parziale.lenght()
		
		//CASO TERMINALE
		if(lettereDisponibili.size()==0) { //equivalente a livello==parola.lenght()
			if(!soluzione.contains(parziale)) { //si potrebbe usare una Set list 
			   this.soluzione.add(parziale);
			}
		}
		
		//CASO NORMALE
		/**
		 * Provo ad aggiungere alla soluzione parziale tutti i caratteri tra quelli disponibili
		 */
		for(Character ch: lettereDisponibili) {
			
			String tentativo = parziale + ch; //creando un tentativo temporaneo non c'è più bisogno del BACKTRACKING
			
			List<Character> listaRimanenti = new ArrayList<>(lettereDisponibili);//non posso rimuovere un elemento da una lista mentre la sto iterando, me ne creo una nuova (CI PIACE POCO)
			listaRimanenti.remove(ch);
			
			cerca(tentativo, livello+1, listaRimanenti);
		}
	}
	
	public List<String> getParolaInDizionario(){
		
		List<String> listaParole = new ArrayList<>();
		for(String s: this.soluzione) {
			if(this.dao.getParolaDaDizionario(s)) {
				listaParole.add(s);
			}
		}
		return listaParole;
	}
	
	public List<String> getParoleNonInDizionario() {

		List<String> listaParole = new ArrayList<>();
		for (String s : this.soluzione) {
			if (!this.dao.getParolaDaDizionario(s)) {
				listaParole.add(s);
			}
		}
		return listaParole;
	}
}


/**
 * Dato di partenza: parola da anagrammare di lunghezza N
 *
 * Soluzione parziale: una parte dell'anagramma già costruito (i primi caratteri)
 * Il livello indica il numero di lettere di cui è composta la soluzione parziale
 * Più scendo di livello più lettere ho nell'anagramma
 * 
 * Soluzione finale: parola di lunghezza N --> caso terminale?
 * Caso terminale --> Salvo le soluzioni trovate
 * 
 * La generazione delle nuove soluzioni passa per il "provare ad aggiumgere"
 * una lettere tra quelle non ancora utilizzate nella soluzione parziale
 */

