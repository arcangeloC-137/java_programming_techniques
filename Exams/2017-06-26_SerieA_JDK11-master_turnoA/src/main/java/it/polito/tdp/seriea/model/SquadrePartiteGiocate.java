package it.polito.tdp.seriea.model;

public class SquadrePartiteGiocate {

	private Team team1;
	private Team team2;
	private Double partiteGiocate;
	/**
	 * @param team1
	 * @param team2
	 * @param d
	 */
	public SquadrePartiteGiocate(Team team1, Team team2, Double d) {
		super();
		this.team1 = team1;
		this.team2 = team2;
		this.partiteGiocate = d;
	}
	public Team getTeam1() {
		return team1;
	}
	public Team getTeam2() {
		return team2;
	}
	public Double getPartiteGiocate() {
		return partiteGiocate;
	}
	
	
}
