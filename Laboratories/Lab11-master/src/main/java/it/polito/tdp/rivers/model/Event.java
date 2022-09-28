package it.polito.tdp.rivers.model;

public class Event implements Comparable<Event>{
	
	public enum EventType{
		INGRESSO_FLUSSO,
		USCITA_FLUSSO
		/*USCITA_FLUSSO_PER_ECCESSO,
		USCITA_FLUSSO_NOT_MIN*/
	}
	
	private EventType tipoEvento;
	private double flusso;
	private int giorno;

	/**
	 * @param tipoEvento
	 * @param capacitaAttuale
	 * @param flusso
	 * @param giorno
	 */
	public Event(EventType tipoEvento, double flusso, int giorno) {
		super();
		this.tipoEvento = tipoEvento;
		this.flusso = flusso;
		this.giorno = giorno;
	}

	
	public EventType getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(EventType tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public double getFlusso() {
		return flusso;
	}

	public void setFlusso(double flusso) {
		this.flusso = flusso;
	}

	public int getGiorno() {
		return giorno;
	}

	public void setGiorno(int giorno) {
		this.giorno = giorno;
	}

	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return this.giorno-o.getGiorno();
	}


	@Override
	public String toString() {
		return "Event [tipoEvento=" + tipoEvento + " flusso=" + flusso
				+ ", giorno=" + giorno + "]";
	}
	
	
}
