package it.polito.tdp.food.model;

public class CondimentAndCalories {
	
	private Condiment condiment;
	private Double calories;
	private int numCibiAdiacenza;
	/**
	 * @param condiment
	 * @param calories
	 * @param numCibiAdiacenza
	 */
	public CondimentAndCalories(Condiment condiment, Double calories, int numCibiAdiacenza) {
		super();
		this.condiment = condiment;
		this.calories = calories;
		this.numCibiAdiacenza = numCibiAdiacenza;
	}
	public Condiment getCondiment() {
		return condiment;
	}
	public void setCondiment(Condiment condiment) {
		this.condiment = condiment;
	}
	public Double getCalories() {
		return calories;
	}
	public void setCalories(Double calories) {
		this.calories = calories;
	}
	public int getNumCibiAdiacenza() {
		return numCibiAdiacenza;
	}
	public void setNumCibiAdiacenza(int numCibiAdiacenza) {
		this.numCibiAdiacenza = numCibiAdiacenza;
	}
	
	
	
}
