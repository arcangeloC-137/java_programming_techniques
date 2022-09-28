package it.polito.tdp.ruzzle.model;

import java.util.ArrayList;
import java.util.List;

public class Ricerca {

	/*
	 * Classe che contiene la ricorsione
	 */
	
	public List<Pos> trovaParola(String parola, Board board) {
		
		for(Pos p: board.getPositions()) {
			
			if(board.getCellValueProperty(p).get().charAt(0) == (parola.charAt(0))) {
				//inizio potenziale della ricorsione
				List<Pos> percorso = new ArrayList<>();
				percorso.add(p);
				
				/*
				 * Passo al metodo ricorsivo:
				 * - La parola da cercare (es. Pippo)
				 * - Il livello iniziale (=1)
				 * - La lista delle posizioni che individuano il percorso
				 * - La griglia delle lettere su cui cercare la soluzione
				 * 
				 */
				if(cerca(parola, 1, percorso, board)) {
					return percorso;
				}
				
			}
		}
		
		return null;
	}

	private boolean cerca(String parola, int livello, List<Pos> percorso, Board board) {
		
		//Caso terminale
		if(livello==parola.length()) {
			//Ho trovato un percorso che individua la parola
			return true;
		}
		
		//Caso intermedio
		
		/*Generazione delle soluzioni:
			   *Trovare tutte le soluzioni che siano:
			    - Adiacenti all'ultima posizione usata
			    - Contenenti la lettera "corretta
			    - Non ancora utilizzare
		  Traduco questo ragionamento in codice
		*/
		
		
		Pos ultima = percorso.get(percorso.size()-1); //Ultima posizione inserita nella soluzione parziale
		/*
		 * Recupero le posizioni adiacenti dalla board attraverso
		 * il metodo già implementato nella classe board
		 */
		List<Pos> adiacenti = board.getAdjacencies(ultima);
		for(Pos p: adiacenti) {
			
			/*
			 * se il percorso NON contiene l'adiacente && 
			 * il carattere è presente nella posizione "livello", 
			 * OK riavvio la ricorsione
			 */
			if(!percorso.contains(p) && (parola.charAt(livello) == board.getCellValueProperty(p).get().charAt(0))) { 
				//Ricorsione
				percorso.add(p);
				if(cerca(parola, livello+1, percorso, board)) {
					return true;
					/*
					 * Uscita rapida in caso di soluzione
					 */
				}
				//backtracking
				percorso.remove(percorso.size()-1);
				
			}
		}
		
		return false;
	}
}
