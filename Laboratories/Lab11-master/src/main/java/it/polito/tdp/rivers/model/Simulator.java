package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import it.polito.tdp.rivers.db.RiversDAO;
import it.polito.tdp.rivers.model.Event.EventType;

public class Simulator {

	//PARAMETRI DELLA SIMULAZIONE
	private  double capienzaTotale; //Q
	private double flussoMedio; //fAvg
	private double capienzaAttuale; //C
	private double flussoOutMin;
	private RiversDAO dao;
	private PriorityQueue<Event> queue;
	private Map<Integer, Double> mappaFlussoMedioByDay= new HashMap<>();
	private List<Flow> listaRilevamenti = new ArrayList<>();
	
	//PARAMETRI DA CALCOLARE
	private int giorniNotErogMinima;
	private double capacitaMedia;

	//INIZIALIZZAZIONE
	private void initialize(River fiume, double K, double fAvg) {
		
		this.dao = new RiversDAO();
		this.queue = new PriorityQueue<>();
		this.mappaFlussoMedioByDay = dao.getAvgFlowByDay(fiume);
		this.listaRilevamenti = dao.getRilevazioniFiume(fiume);
		
		this.giorniNotErogMinima=0;
		this.capacitaMedia=0.0;
		this.capienzaTotale = K*fAvg*(30*3600*24); // Capacità totale del bacino espressa in metri cubi
		this.capienzaAttuale = this.capienzaTotale/2; // Quantità di acqua presente all'interno del bacino ad inizio simulazione
		this.flussoMedio = fAvg; //espresso in m^3/s
		this.flussoOutMin = 0.8*this.flussoMedio*(3600*24); // Flusso minimo in uscita da garantire al giorno
		
		
		/*LocalDate giorno = LocalDate.of(2000, 1, 1); // Giorno di inizio simulazione forfettario
		int countGiorni = 1;
		
		while(countGiorni<366){
		
			Event nuovo = new Event(EventType.INGRESSO_FLUSSO, this.mappaFlussoMedioByDay.get(giorno.getDayOfMonth())*3600*24, countGiorni);
			queue.add(nuovo);
			countGiorni++;
			giorno = giorno.plusDays(1);
			//System.out.println(nuovo.toString());
		}*/
		
		int count = 0;
		while(count<this.listaRilevamenti.size()) {
			Event nuovo = new Event(EventType.INGRESSO_FLUSSO, this.listaRilevamenti.get(count).getFlow()*3600*24, count);
			queue.add(nuovo);
			count++;
		}
		System.out.println("Flusso medio del corso d'acqua: "+this.flussoMedio+" m^3/s\n");
		System.out.println("Fattore di scala: "+K+"\n");
		System.out.println("Capienza totale del bacino: "+this.capienzaTotale+" m^3\n");
		System.out.println("Flusso minimo richiesto in uscita: "+this.flussoOutMin+" m^3\n");
		
	}

	public void run(River fiume, double fattoreScala, double flussoMedio) {
		
		initialize(fiume, fattoreScala, flussoMedio);
		
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
			System.out.println(e.getGiorno());
		}
		
	}

	private void processEvent(Event e) {
		
		switch(e.getTipoEvento()) {
		
		case INGRESSO_FLUSSO:
			
			//Sommo il flusso in ingresso con la capacità del bacino, se vedo se riesco a fornire la qta minima
			
			//CAPACITA' DEL BACINO
			double flussoDisponibile = e.getFlusso()+this.capienzaAttuale; //C
			System.out.println("Capienza attuale: "+this.capienzaAttuale);
			System.out.println("Flusso in ingresso: "+e.getFlusso());
			System.out.println("Flusso disponibile in output: "+flussoDisponibile);
			double flussoInUscita=0;
			double flussoMinRichiesto;
			
			//Flusso minimo richiesto:
			double probabilita = 0.05;
			double rand = Math.random();
			if(rand<=probabilita) {
				flussoMinRichiesto = this.flussoOutMin*10;
			}else {
				flussoMinRichiesto = this.flussoOutMin;
			}
			System.out.println("Flusso richiesto in uscita: "+flussoMinRichiesto);
			
			//SCENARIO 1.
			//Il flusso disponibile è minore o uguale della capienza totale del bacino Q
			if(flussoDisponibile<=this.capienzaTotale) { //C<=Q
				
				//CASO 1.
				//Se il flusso disponibile è maggiore o uguale a quello minimo richiesto, posso soddisfare la richiesta
				if(flussoDisponibile>=flussoMinRichiesto) {
					flussoInUscita = flussoMinRichiesto;
				}
				
				//CASO 2.
				//Il flusso disponibile è minore del minimo richiesto, fornisco quello che posso
				if(flussoDisponibile<flussoMinRichiesto) {
					flussoInUscita = flussoDisponibile;
					this.giorniNotErogMinima++;
				}
				
			}else { //SCENARIO 2. (TRACIMAZIONE) C>Q
				
				//Il flusso disponibile è maggiore della capienza totale del bacino Q, va scaricata
				
				double flussoInEccesso = flussoDisponibile-this.capienzaTotale;
				
				if(flussoInEccesso>flussoMinRichiesto) {
					
					//Se ho del flusso in eccesso maggiore del flusso minimo, devo comunque scaricarlo tutto
					flussoInUscita = flussoInEccesso;
					
				}else {
					
					//Se il flusso in eccesso è minore del flusso minimo richiesto, mi basta scaricare il flusso minimo
					flussoInUscita = flussoMinRichiesto;
				}
			}
			
			
			
			Event nuovo = new Event(EventType.USCITA_FLUSSO, flussoInUscita, e.getGiorno());
			this.queue.add(nuovo);
			this.capacitaMedia+=flussoDisponibile;
			this.capienzaAttuale = flussoDisponibile-flussoInUscita;
			System.out.println("Capienza dopo output: "+this.capienzaAttuale);
			break;
			
		case USCITA_FLUSSO:
			break;
		}
		
	}
	
	public int getGiorniNotErogazioneMin() {
		return this.giorniNotErogMinima;
	}
	
	public double getCapacitaMedia() {
		
		double capacita = this.capacitaMedia/365;
		return Math.ceil(capacita);
	}
}
