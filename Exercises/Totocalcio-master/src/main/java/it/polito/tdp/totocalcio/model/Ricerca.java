package it.polito.tdp.totocalcio.model;

import java.util.ArrayList;
import java.util.List;

public class Ricerca {

	private Pronostico pronostico;
	private int N;
	private List<Risultato> soluzione;
	
	public List<Risultato> cerca(Pronostico pronostico) {
		
		this.pronostico=pronostico;
		this.N=pronostico.size();
		
		List<RisultatoPartita> parziale = new ArrayList<>();
		int livello=0;
		this.soluzione = new ArrayList<>();
		
		ricorsiva(parziale, livello);
		
		return this.soluzione; //TODO: restituire l'elenco
		
	}
	
	//la vera ricorsione avviene qui sotto
	private void ricorsiva(List<RisultatoPartita> parziale, int livello) {
		
		//CASO TERMINALE
		if(livello==N) { //Con N lunghezza del pronostico
			
			//Questa sol. parziale in realtà è completa
			//System.out.println(parziale);
			this.soluzione.add(new Risultato(parziale));
			
		}else {
			//SONO ANCORA NEL CASO GENERALE
			
			//[parziale da 0 a livello-1] [livello] [livello+1 in poi]
			
			//pp sono i sottoproblemi da provare (es. "2 e X")
			PronosticoPartita pp = pronostico.get(livello);
			
			for(RisultatoPartita ris: pp.getRisultati()) {
				
				//provo a mettere 'ris' bella posizione 'livello'
				//della sol.  parziale
				
				//COSTRUZIONE DELLA SOLUZIONE PARZIALE (SOTTOPROBLEMA)
				parziale.add(ris);
				//CHIAMATA RICORSIVA
				ricorsiva(parziale, livello+1);
				//BACKTRACKING, contorllo se la soluzione inserita vada bene (es. ho provato a mettere 2, non andava bene, provo con X, togliendo il 2)
				parziale.remove(parziale.size()-1);
				
			}
		}
	}

}

/*
 * Livello= numero di partita che sto considerando
 * le partite da a livello-1 sono già state decise
 * le partite di indice livello la devo decidere io
 * le partite da livelo+1 in poi le deciderano le procedure ricorsive sottostanti
 * 
 * Soluzione parziale: una lista di RisultatoPartita di lunghezza pari al livello
 * 
 * Soluzione totale: ho N risultati
 * 
 * Condizione di terminazione_ livello = N
 * 
 * Generazione delle soluzonei del livello: provando tutti i pronostici
 * definiti per quel livello.
 * 
 */
/*[ "2X", "1", "1X2", "12"]
		
	2    1     X      2   []

["2X"] + [ "1", "1X2", "12"]
		 [ "1", "1X2", "12"]
		 [ "1"] + [ "1X2", "12"]
*/