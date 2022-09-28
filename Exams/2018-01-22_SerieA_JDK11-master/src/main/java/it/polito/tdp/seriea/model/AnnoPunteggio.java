package it.polito.tdp.seriea.model;

public class AnnoPunteggio {

	private Season stagione;
	private Integer punteggio;
	/**
	 * @param stagione
	 * @param punteggio
	 */
	public AnnoPunteggio(Season stagione, Integer punteggio) {
		super();
		this.stagione = stagione;
		this.punteggio = punteggio;
	}
	public Season getStagione() {
		return stagione;
	}
	public Integer getPunteggio() {
		return punteggio;
	}
	
	
}
