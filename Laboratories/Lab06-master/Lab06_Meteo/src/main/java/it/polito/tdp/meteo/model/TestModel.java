package it.polito.tdp.meteo.model;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model m = new Model();
		
		System.out.println(m.getUmiditaMedia(12));
		System.out.println(m.calcolaSequenza(Month.APRIL));
	}

}















/*	/**
	 * @param parziale
	 * @param i indica dove mi trovo nella lista "rilevamenti"
	 * @param k indica il livello della soluzione (MAX 15)
	 *//*
	private void cerca(List<Rilevamento> parziale, int k, int i, List<Rilevamento> giorniDisponibili) {
		
		//CASO TERMINALE
		
		if(k>NUMERO_GIORNI_TOTALI) {
			return;
		}
		
		//ho trovato un ramo di ricorsione non accettabile
		if(erroreSoluzioneParziale(parziale)) {
			return;
		}
		
		if(parziale.size()==NUMERO_GIORNI_TOTALI) {
			
			if (!erroreSoluzioneParzialeCompleta(parziale)) {
				double costo = costoSoluzione(parziale);
				if (costo < bestCosto) {
					bestCosto = costo;
					bestSoluzione = new ArrayList<>(parziale);
				}
			}
		}
		
		// ho provato tutti gli elementi presenti in "rilevamenti"
		if (giorniDisponibili.size()==0) {
			return;
		}
		//CASO GENERALE
		
		parziale.add(giorniDisponibili.get(i));
		
		List<Rilevamento> giorniRimanenti = new ArrayList<>(giorniDisponibili);
		Date giornoDaRimuovere = rilevamenti.get(i).getData();
		for(Rilevamento r1: giorniDisponibili) {
			if(r1.getData().compareTo(giornoDaRimuovere)==0) {
				giorniRimanenti.remove(r1);
			}
		}
		
		cerca(parziale, i+1, k+1, giorniRimanenti);
		parziale.remove(giorniDisponibili.get(i));
		cerca(parziale, i+1, k+1, giorniDisponibili);
		
	}
/*
	private boolean erroreSoluzioneParzialeCompleta(List<Rilevamento> parziale) {

		int contatoreGiorniConsecutivi = 0;
		String cittaDiControllo = parziale.get(0).getLocalita();
		
		for(Rilevamento r: parziale) {
			if(r.getLocalita().compareTo(cittaDiControllo)==0) {
				contatoreGiorniConsecutivi++;
			}
			
			if(r.getLocalita().compareTo(cittaDiControllo)!=0 && contatoreGiorniConsecutivi>2) {
				cittaDiControllo = r.getLocalita();
				contatoreGiorniConsecutivi = 1;
			}
			
			if(r.getLocalita().compareTo(cittaDiControllo)!=0 && contatoreGiorniConsecutivi<2) {
				return true;
			}
		}
		return false;
	}

*/




















/*
private void cerca(List<Rilevamento> parziale, int L, int i, List<Rilevamento> giorniDisponibili) {
	
	//CASO TERMINALE
	
	//Ho provato tutti gli elementi della lista, ritorno
	if(L== NUMERO_GIORNI_TOTALI) {
		return;
	}
	
	// Ho trovato una soluzione parziale
	if (parziale.size() == NUMERO_GIORNI_TOTALI) {

		if(!controllaParziale(parziale)) {
			return;
		}
		double costo = calcolaCosto(parziale);
		if (costo < bestCosto) {

			bestSoluzione = new ArrayList<>(parziale);
			bestCosto = costo;
		}
	}
	

	//CASO GENERALE
	/*
	for(Rilevamento r: giorniDisponibili) {
		
		parziale.add(r);
		List<Rilevamento> giorniRimanenti = new ArrayList<>(rimuoviGiorni(giorniDisponibili, r));
		cerca(parziale, L+1, giorniRimanenti);
		
		parziale.remove(r);
		cerca(parziale, L+1, giorniDisponibili);
	
	}
	*/
	/*
	parziale.add(giorniDisponibili.get(i));
	List<Rilevamento> giorniRimanenti = new ArrayList<>(rimuoviGiorni(giorniDisponibili, giorniDisponibili.get(i)));
	cerca(parziale, L+1, giorniRimanenti.size()-1, giorniRimanenti);
	
	parziale.remove(giorniDisponibili.get(L));
	cerca(parziale, L+1, giorniRimanenti.size()-1, giorniDisponibili);
	
}

private boolean controllaParziale(List<Rilevamento> parziale) {
	
	//CONTROLLI PER TAGLIARE RAMI NON ACCETTABILI
	
			// taglia tutte le soluzioni che hanno date non in ordine crescente
			if(controllaOrdinamentoCrescente(parziale)) {
				return false;
			}
			
			
			// taglio la soluzione dove ci si è fermati per più di 6 giorni nella città L-esima
			if(controllaNumeroGiorniMax(parziale)){
				return false;
			}
			
			
			// controllo che il tecnico sia rimasto almeno tre giorni consecutivi nella stessa città
			if (controlloNumeroGiorniConsecutiviMin(parziale)) {
				return false;
			}
	return true;
}

private List<Rilevamento> rimuoviGiorni(List<Rilevamento> giorniDisponibili, Rilevamento r) {
	
	List<Rilevamento> giorniRimanenti = new ArrayList<>(giorniDisponibili);
	for(Rilevamento ril: giorniDisponibili) {
		if(ril.getData().compareTo(r.getData())==0) {
			giorniRimanenti.remove(ril);
		}
	}
	return giorniRimanenti;
}

private boolean controllaOrdinamentoCrescente(List<Rilevamento> parziale) {
	
	if(parziale.size()>1) {
		for(int i=0; i<parziale.size()-1; i++) {
			if(parziale.get(i).getData().compareTo(parziale.get(i+1).getData())>0) {
				return true;
			}
		}
		return false;
	}
	
	return false;
}

private boolean controlloNumeroGiorniConsecutiviMin(List<Rilevamento> parziale) {

	if(parziale.size()>1) {
		if(parziale.get(parziale.size()-1).getLocalita().compareTo(parziale.get(parziale.size()-2).getLocalita())==0) {
			return false;
		}else {
			if(parziale.size()<4) {
				return true;
			}else {
				
				for(int i=parziale.size()-4; i<parziale.size()-1; i++) {
					if(parziale.get(i).getLocalita().compareTo(parziale.get(i+1).getLocalita())!=0) {
						return true;
					}
				}
				return false;
			}
		}
	}
	return false;
}

private boolean controllaNumeroGiorniMax(List<Rilevamento> parziale) {
	
    if (parziale.size() > 0) {
		int count = 0;
		String localita = parziale.get(parziale.size() - 1).getLocalita();
		for (Rilevamento r : parziale) {
			if(r.getLocalita().compareTo(localita)==0) {
				count++;
			}
		}
		if (count > NUMERO_GIORNI_CITTA_MAX) {
			return true;
		}
		return false;
	}
	
	return false;
}

private double calcolaCosto(List<Rilevamento> parziale) {

	double result=0.0;
	
	for(int i=0; i<parziale.size(); i++) {
		
		/*if(parziale.get(i).getLocalita().compareTo(parziale.get(i+1).getLocalita())!=0) {
			result+=COST;
		}*//*
		result+=parziale.get(i).getUmidita();
	}
	return result;
	
}

*/















/*private void cerca(List<RilevamentoTecnico> parziale, int i, int giorni, double costo){
	
	
	//CASI TERMINALI
	
	//Sono rimasto per più di 6 giorni (anche non consecutivi) nella stessa città,
    //taglio il ramo dell'albero di ricorsione
	if (parziale.size() > 0) {
		String localitaControllo = parziale.get(parziale.size()-1).getLocalita();
		int count = 0;
		for (RilevamentoTecnico r : parziale) {
			if (r.getLocalita().compareTo(localitaControllo) == 0) {
				count++;
			}
		}

		if (count > 2) {
			return;
		}
	}
	
	if(parziale.size()>1) {
		//tolgo le soluzioni con date precedenti all'ultima
		if(parziale.get(parziale.size()-1).getData().compareTo(parziale.get(parziale.size()-2).getData())<=0) {
			return;
		}
	}
	
	//Ho raggiunto i quindi giorni (soluzione ottimale)
	if(giorni==(NUMERO_GIORNI_TOTALI/NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN)) {
		
		if(costo<bestCosto) {
			bestCosto=costo;
			bestSoluzione = new ArrayList<>(parziale);
			}
	}
	
	if(giorni>5) {
		return;
	}
	
	if(i==rilevamenti.size()) {
		return;
	}
	//CASO GENERALE
	//Provo a inserire
	parziale.add(rilevamenti.get(i));
	costo+=rilevamenti.get(i).getCosto();
	cerca(parziale, i+1, giorni+1, costo);
	
	//Faccio backtracking
	parziale.remove(rilevamenti.get(i));
	//Provo a non inserire
	cerca(parziale, i+1, giorni, costo);
	
}
*/



/*
 * private void cerca(List<RilevamentoTecnico> parziale, int L) {
		
		if (parziale.size() > 0) {
			String localitaControllo = parziale.get(parziale.size()-1).getLocalita();
			int count = 0;
			for (RilevamentoTecnico r : parziale) {
				if (r.getLocalita().compareTo(localitaControllo) == 0) {
					count++;
				}
			}

			if (count > 2) {
				return;
			}
		}
		
		
		if(parziale.size()>1) {
			//tolgo le soluzioni con date precedenti all'ultima
			if(parziale.get(parziale.size()-1).getData().compareTo(parziale.get(parziale.size()-2).getData())<=0) {
				return;
			}
		}
		
		if(L==(NUMERO_GIORNI_TOTALI/3)) {
			
			if(calcolaCosto(parziale)<bestCosto) {
				bestCosto=calcolaCosto(parziale);
				this.bestSoluzione = new ArrayList<>(parziale);
			}
		}
		
		//CASO GENERALE
		for(RilevamentoTecnico r: rilevamenti) {
			
			//provo ad aggiungere la soluzione
			parziale.add(r);
			cerca(parziale, L+1);
			
			//backtracking
			parziale.remove(parziale.size()-1);
		}
		
		
	}
	
	
	private double calcolaCosto(List<RilevamentoTecnico> parziale) {

		double result=0.0;
		for(RilevamentoTecnico r: parziale) {
			result+=r.getCosto();
		}
		
		for(int i=0; i<parziale.size(); i++) {
			if(i>0) {
				if(parziale.get(i).getLocalita().compareTo(parziale.get(i-1).getLocalita())!=0) {
					result+=100;
				}
			}
		}
		return result;
	}
	
	
 */
/*
rilevamenti = new ArrayList<>(getRilevamentiTecnici(this.meteoDB.getAllRilevamentiLocalitaPrimaMetaMese(mese)));
List<RilevamentoTecnico> parziale = new ArrayList<>();

for (RilevamentoTecnico r : rilevamenti) {

	System.out.println(r.getLocalita()+" "+r.getData()+" "+r.getCosto());
}

cerca(parziale, 0);

String result = "Sequenza ottimale:\n";
for(RilevamentoTecnico r: bestSoluzione) {
	
	result+=r.getLocalita()+" fino al "+r.getData()+"\n";
}
result+="Costo totale: "+bestCosto+"\n";
return result;
*/

/*
 * Condenso i 15 giorni in ogni città a 3 a 3, così da rifurre i campi di ricorsione
 */
/*
private List<RilevamentoTecnico> getRilevamentiTecnici(List<Rilevamento> rilevamenti) {

	int count = 0;
	double costo = 0.0;
	List<RilevamentoTecnico> temp = new ArrayList<>();

	for (Rilevamento r : rilevamenti) {

		if (count < 3) {
			costo += r.getUmidita();
		}
		count++;
		if (count == 3) {
			temp.add(new RilevamentoTecnico(r.getLocalita(), costo, r.getData()));
			count = 0;
			costo = 0;
		}
	}

	return temp;
}*/