package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO podao;
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

	public List<Blackout> findWorstCase(Nerc nerc, int maxY, int maxH) {
		
		Ricerca ricerca = new Ricerca();
		return ricerca.getWorstCase(new ArrayList<>(podao.getBlackoutFromNERC(nerc)), maxY, maxH);
	}
}
