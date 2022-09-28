package it.polito.tdp.borders.model;

public class Country {
	
	private String stateAbb;
	private int ccCode;
	private String stateName;
	/**
	 * @param stateAbb
	 * @param ccCode
	 * @param stateName
	 */
	public Country(String stateAbb, int ccCode, String stateName) {
		super();
		this.stateAbb = stateAbb;
		this.ccCode = ccCode;
		this.stateName = stateName;
	}
	public String getStateAbb() {
		return stateAbb;
	}
	public void setStateAbb(String stateAbb) {
		this.stateAbb = stateAbb;
	}
	public int getCcCode() {
		return ccCode;
	}
	public void setCcCode(int ccCode) {
		this.ccCode = ccCode;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ccCode;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (ccCode != other.ccCode)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return stateAbb + " "+ stateName+"\n";
	}
	
	
	
}
