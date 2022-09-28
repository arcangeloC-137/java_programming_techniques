package it.polito.tdp.meteo.model;

import java.util.Date;

public class RilevamentoTecnico {

	private String localita;
	private double costo;
	private Date data;
	
	/**
	 * @param localita
	 * @param costo
	 * @param data: data del terzo giorno
	 */
	public RilevamentoTecnico(String localita, double costo, Date data) {
		super();
		this.localita = localita;
		this.costo = costo;
		this.data = data;
	}

	public String getLocalita() {
		return localita;
	}

	public void setLocalita(String localita) {
		this.localita = localita;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public Date getData() {
		return data;
	}

	public void setGiorni(Date data) {
		this.data = data;
	}
	
	
}
