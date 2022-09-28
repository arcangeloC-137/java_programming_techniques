package polito.it.noleggio.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.PriorityQueue;

import polito.it.noleggio.model.Event.EventType;

public class Simulator {
	
	/*
	 * Struttura dati principale: CODA PRIORITARIA
	 * Una coda prioritaria composta da eventi di simulazione. 
	 * Diventa necessario creare una classe separata "Event"
	 */
	
	//CODA DEGLI EVENTI
	private PriorityQueue<Event> queue = new PriorityQueue<>();
	
	//1.Parametri di simulazione (impostabili dall'esterno, necessito dei setter)
	private int NC = 10; //Numero totale di auto, con default pari a 10
	private Duration T_IN = Duration.of(10, ChronoUnit.MINUTES); //intervallo tra i clienti, con default pari a 10
	private final LocalTime oraApertura = LocalTime.of(8, 00);
	private final LocalTime oraChiusura = LocalTime.of(17, 00);
	
	//2.Modello del Mondo
	/*
	 * Le auto disponibili vanno da 0 ad NC
	 */
	private int nAuto; //Auto disponibili in deposito, auto inizializzato dal simulatore
	
	//3.Valori da calcolare
	private int clienti;
	private int insoddisfatti;
	/*
	 * Definisco solo dei getter perché non ha senso settare i parametri da calcolare
	 */
	
	public int getClienti() {
		return clienti;
	}

	public int getInsoddisfatti() {
		return insoddisfatti;
	}

	//METODI PER IMPOSTARE I PARAMETRI (setter)
	public void setNumCars(int N) {
		this.NC = N;
	}
	
	public void setClientFrequency(Duration d) {
		this.T_IN = d;
	}
	
	//SIMULAZIONE VERA E PROPRIA
	public void run() {
		/*
		 * Preparazione iniziale. Preparo le variabili del mondo
		 * e della coda degli eventi. 
		 */
		
		this.nAuto = this.NC; //quando apro, ho tutte le auto in deposito
		this.clienti = 0; //non ho ancora clienti
		this.insoddisfatti = 0; //non ho ancora clienti insoddisfatti
		
		this.queue.clear(); //Non voglio partire con eventi di giornate precedenti
		LocalTime oraDiArrivoCliente = this.oraApertura; //Il primo cliente arriva a quest'ora
		
		do {
			
			Event e = new Event(oraDiArrivoCliente, EventType.NEW_CLIENT);
			this.queue.add(e);
			oraDiArrivoCliente = oraDiArrivoCliente.plus(this.T_IN);
			
		}while(oraDiArrivoCliente.isBefore(this.oraChiusura));
		
		
		/*
		 * Esecuzione del ciclo di simulazione
		 */
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll(); //Estraggo gli eventi dalla coda in ordine di data, o tempo, per come abbiamo settato il compare in EVENT
			/*switch(e.getType()) { //METODO PROCESS EVENT, non è cattiva idea fare questa operazione in un metodo separato
			
			case NEW_CLIENT: 
				break;
				
			case CAR_RETURNED:
				break;
			}*/
			System.out.println(e);
			processEvent(e);
		}
	}
	
	public void processEvent(Event e) {
		
		switch(e.getType()) { 
		
		case NEW_CLIENT: 
			
			if(this.nAuto>0) { //Ho almeno un'auto ancora in deposito da poter dare al Cliente??
				//Cliente nuovo, servito, auto noleggiata
				
				//1.AGGIORNO IL MODELLO DEL MONDO
				this.nAuto--; //l'auto è stata noleggiata
				
				//2.AGGIORNO I RISULTATI
				this.clienti++; //è arrivato un nuovo cliente
				
				//3.GENERO UN NUOVO EVENTO
				/*
				 * Devo generare un evento del tipo RESTITUISCI. 
				 * PROBLEMA: quando verrà restituita?? 
				 * Variabile di tipo stocastico
				 */
				double num = Math.random(); //[0,1)
				Duration travel;
				if(num<(1.0/3.0)) {
					travel = Duration.of(1, ChronoUnit.HOURS);
				}else if(num<(2.0/3.0)) {
					travel = Duration.of(2, ChronoUnit.HOURS);
				}else {
					travel = Duration.of(3, ChronoUnit.HOURS);
				}
				
				Event nuovo = new Event(e.getTime().plus(travel), EventType.CAR_RETURNED); //Costruisco un nuovo evento
				this.queue.add(nuovo);
				
			}else {
				//Cliente nuovo, insoddisfatto
				this.clienti++;
				this.insoddisfatti++;
			}
			break;
			
		case CAR_RETURNED:
			
			this.nAuto++;
			break;
		}

		
	}
}
