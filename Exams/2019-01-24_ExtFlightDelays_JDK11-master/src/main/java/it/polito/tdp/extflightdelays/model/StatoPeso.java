package it.polito.tdp.extflightdelays.model;

public class StatoPeso{
	
	private String stato;
	private Double peso;
	/**
	 * @param stato
	 * @param peso
	 */
	public StatoPeso(String stato, Double peso) {
		super();
		this.stato = stato;
		this.peso = peso;
	}
	public String getStato() {
		return stato;
	}
	public Double getPeso() {
		return peso;
	}

	
}
