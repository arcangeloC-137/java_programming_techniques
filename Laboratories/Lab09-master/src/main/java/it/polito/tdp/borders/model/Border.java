package it.polito.tdp.borders.model;

public class Border {

	private Country stateOne;
	private Country stateTwo;
	/**
	 * @param stateOne
	 * @param stateTwo
	 */
	public Border(Country stateOne, Country stateTwo) {
		super();
		this.stateOne = stateOne;
		this.stateTwo = stateTwo;
	}
	public Country getStateOne() {
		return stateOne;
	}
	public void setStateOne(Country stateOne) {
		this.stateOne = stateOne;
	}
	public Country getStateTwo() {
		return stateTwo;
	}
	public void setStateTwo(Country stateTwo) {
		this.stateTwo = stateTwo;
	}
	
	
}
