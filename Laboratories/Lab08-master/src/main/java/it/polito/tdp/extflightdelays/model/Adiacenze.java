package it.polito.tdp.extflightdelays.model;

public class Adiacenze {

	//CLASSE DI SUPPORTO PER LA COSTRUZIONE DEL GRAFO
	
	private Airport airport1;
	private Airport airport2;
	private int distanceBetween;
	/**
	 * @param airport1 (AEROPORTO DI PARTENZA)
	 * @param airport2 (AEROPORTO DI DESTINAZIONE)
	 * @param distanceBetween (PESO DELL'ARCO)
	 */
	public Adiacenze(Airport airport1, Airport airport2, int distanceBetween) {
		super();
		this.airport1 = airport1;
		this.airport2 = airport2;
		this.distanceBetween = distanceBetween;
	}
	public Airport getAirport1() {
		return airport1;
	}
	public void setAirport1(Airport airport1) {
		this.airport1 = airport1;
	}
	public Airport getAirport2() {
		return airport2;
	}
	public void setAirport2(Airport airport2) {
		this.airport2 = airport2;
	}
	public int getDistanceBetween() {
		return distanceBetween;
	}
	public void setDistanceBetween(int distanceBetween) {
		this.distanceBetween = distanceBetween;
	}
	
	
	
}
