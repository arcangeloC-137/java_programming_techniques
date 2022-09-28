package it.polito.tdp.extflightdelays.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.extflightdelays.model.Adiacenze;
import it.polito.tdp.extflightdelays.model.Airport;

public class TestDAO {

	public static void main(String[] args) {

		ExtFlightDelaysDAO dao = new ExtFlightDelaysDAO();
		Map<Integer, Airport> idMap = new HashMap<>();
		
		dao.loadAllAirports(idMap);
		List<Adiacenze> adiacenze = new ArrayList<>(dao.getEdges(idMap));
		
		System.out.println(String.format("Numero di vertici: %d", adiacenze.size()));
		
		
	}

}
