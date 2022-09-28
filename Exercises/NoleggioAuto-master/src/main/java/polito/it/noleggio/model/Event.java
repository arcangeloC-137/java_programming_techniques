package polito.it.noleggio.model;

import java.time.LocalTime;

public class Event implements Comparable<Event>{
	
	/*
	 * Un evento è costituito ALMENO da due campi sempre presenti:
	 *  - Tempo
	 *  - Tipo di evento (può essere comodo definire una enumerazione, per definire una serie di costanti)
	 */
	
	public enum EventType {
		//dichiariamo qui le costanti
		NEW_CLIENT, CAR_RETURNED //corrispondenti ai due eventi, convertiti da Java in 0 e 1
	}
	
	//ATTRIBUTI DELLA CLASSE EVENTO:
	/*
	 * In situazioni più complesse potrebbero volercene di più
	 */
	private LocalTime time;
	private EventType type;
	
	/*
	 * LA CLASSE DEVE IMPLEMENTARE L'INTERFACCIA COMPARABLE 
	 * PER POTER ORDINARE GLI EVENTI IN UNA CODA PRIORITARIA
	 */
	
	/**
	 * @param time
	 * @param type
	 */
	public Event(LocalTime time, EventType type) {
		super();
		this.time = time;
		this.type = type;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	@Override
	public int compareTo(Event other) {
		
		return this.time.compareTo(other.time);
	}

	@Override
	public String toString() {
		return "Event [time=" + time + ", type=" + type + "]";
	}
	
	
	
}
