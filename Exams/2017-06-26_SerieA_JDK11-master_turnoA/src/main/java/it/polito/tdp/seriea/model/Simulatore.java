package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Simulatore {
	
	//PARAMETRI DELLA SIMULAZIONE
	private Map<Team, Integer> mappaSquadraTifosi; //Team (Squadra), Integer (num tifosi)
	private List<Match> listaIncontri;
	private List<Team> listaSquadre;
	private final int pFactor = 10;
	private final int tifosi = 1000;
	
	//PARAMETRI DA CALCOLARE
	
	
	public void initialize(List<Match> list, List<Team> listaSquadreCampionato) {
		
		this.listaIncontri = new ArrayList<>(list);
		this.listaSquadre = new ArrayList<>(listaSquadreCampionato);
		this.mappaSquadraTifosi = new HashMap<>();
		
		for(Team t: this.listaSquadre) {
			this.mappaSquadraTifosi.put(t, this.tifosi);
		}
		
	}

	public void run() {
		
		for(Match m: this.listaIncontri) {
			
			Team squadraA = m.getHomeTeam();
			Team squadraB = m.getAwayTeam();
			int ptA = m.getFthg();
			int ptB = m.getFtag();
			
			if(ptA>ptB) {
				
				double prob = 1-(ptB/ptA);
				double random = Math.random();
				
				if(random<=prob) {
					ptB-=1;
				}
				
				double percTifosi = (ptA-ptB)*this.pFactor;
				int tifosi = (int) Math.round(this.mappaSquadraTifosi.get(squadraB)*(percTifosi/100));
				
				this.mappaSquadraTifosi.replace(squadraB, this.mappaSquadraTifosi.get(squadraB)-tifosi);
				this.mappaSquadraTifosi.replace(squadraA, this.mappaSquadraTifosi.get(squadraA)+tifosi);
				
				for(Team t: this.mappaSquadraTifosi.keySet()) {
					if(t.equals(squadraA)) {
						t.setPunteggio(t.getPunteggio()+3);
					}
				}
				
			}else if(ptA<ptB){
				
				double prob = 1-(ptA/ptB);
				double random = Math.random();
				
				if(random<=prob) {
					ptA-=1;
				}
				
				double percTifosi = (ptB-ptA)*this.pFactor;
				int tifosi = (int) Math.round(this.mappaSquadraTifosi.get(squadraA)*(percTifosi/100));
				
				this.mappaSquadraTifosi.replace(squadraA, this.mappaSquadraTifosi.get(squadraA)-tifosi);
				this.mappaSquadraTifosi.replace(squadraB, this.mappaSquadraTifosi.get(squadraB)+tifosi);
				
				for(Team t: this.mappaSquadraTifosi.keySet()) {
					if(t.equals(squadraB)) {
						t.setPunteggio(t.getPunteggio()+3);
					}
				}
			}else {
				
				for(Team t: this.mappaSquadraTifosi.keySet()) {
					if(t.equals(squadraB)) {
						t.setPunteggio(t.getPunteggio()+1);
					}
					
					if(t.equals(squadraA)) {
						t.setPunteggio(t.getPunteggio()+1);
					}
				}
			}
		}
	}

	public Map<Team, Integer> getSquadrePuntiTifosi() {
		// TODO Auto-generated method stub
		return mappaSquadraTifosi;
	}

}
