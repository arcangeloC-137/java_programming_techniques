package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.List;

public class Ricerca {

	//CLASSE CONTENENTE LA RICORSIONE
	
	private List<Blackout> listaBlackout;
	private int maxY;
	private int maxH;
	private int best;
	private List<Blackout> soluzione;

	
	/**
	 * 
	 * @param listaBlackout
	 * @param maxY (numero massimo di anni su cui effettuare la ricerca)
	 * @param maxH (numero massimo di ore per il worst case)
	 * @return
	 */
	public List<Blackout> getWorstCase(List<Blackout> listaBlackout, int maxY, int maxH) {
		
		this.best = 0;
		this.listaBlackout = new ArrayList<>(listaBlackout);
		this.maxH = maxH;
		this.maxY = maxY;
		List<Blackout> parziale = new ArrayList<>();
		cerca(parziale, 0);
		return soluzione;
	}

	/**
	 * @param soluzione (Lista di Blackout contenente il WORST CASE)
	 * @param maxY
	 * @param maxH
	 * @param livello (corrispondente al numero di ore)
	 */
	private void cerca(List<Blackout> parziale, int livello) {
		
		int temp = calcolaBest(parziale);
		if (temp > best) {
			best = temp;
			soluzione = new ArrayList<>(parziale);
		} else {
			// CASO GENERALE
			for (Blackout b : listaBlackout) {

				if ((!parziale.contains(b)) && blackoutInseribile(parziale, b, livello)) {

					parziale.add(b);
					cerca(parziale, livello + b.getOreDisservizio());
					parziale.remove(parziale.size() - 1);

				}
			}
		}
		
	}
	

	private int calcolaBest(List<Blackout> parziale) {
		int ris=0;
		for(Blackout b: parziale) {
			ris+=b.getCustomersAffected();
		}
		return ris;
	}

	private boolean blackoutInseribile(List<Blackout> parziale, Blackout b, int oreBlackout) {

		//Controllo che gli anni di differenza tra la prima e l'ultima data non siano maggiori a maxY
		if (parziale.size() > 0) {
			if ((b.getData_fine().getYear() - parziale.get(0).getData_inizio().getYear()) > maxY) {
			
				return false;
			}
		}
		
		//Controllo che non venga superato il limite massimo di ore
		int oreTotaliBKOUT = b.getOreDisservizio()+oreBlackout;
		
		if(oreTotaliBKOUT>=maxH) {

			return false;
		}
		
		return true;
	}

	
}