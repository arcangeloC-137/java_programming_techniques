package it.polito.tdp.borders.model;

public class Event implements Comparable<Event>{

	public enum EventType {
		//Esiste un solo evento
		EMIGRAZIONE
	}
	
	//Attributi dei singoli eventi:
	private EventType tipoEvento; 
	private int tempo;
	private int numPersone;
	private Country paese;
	
	/**
	 * @param tipoEvento [Emigrato]
	 * @param tempo [Indicativo, si parte da T=1]
	 * @param numPersone [Persone interessate nel singolo evento]
	 */
	public Event(EventType tipoEvento, int tempo, int numPersone, Country paese) {
		super();
		this.tipoEvento = tipoEvento;
		this.tempo = tempo;
		this.numPersone = numPersone;
		this.paese = paese;
	}

	public EventType getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(EventType tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public int getTempo() {
		return tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}

	public int getNumPersone() {
		return numPersone;
	}

	public void setNumPersone(int numPersone) {
		this.numPersone = numPersone;
	}

	public Country getPaese() {
		return paese;
	}

	public void setPaese(Country paese) {
		this.paese = paese;
	}

	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return this.tempo-o.getTempo();
	}

	@Override
	public String toString() {
		return "Event [tipoEvento=" + tipoEvento + ", tempo=" + tempo + ", numPersone=" + numPersone + ", paese="
				+ paese + "]";
	}


	
	
}
