package it.polito.tdp.rivers.db;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

public class TestRiversDAO {

	public static void main(String[] args) {
		RiversDAO dao = new RiversDAO();
		
		
		River fiume = dao.getAllRivers().get(4);
		List<Flow> list = new ArrayList<>(dao.getRilevazioniFiume(fiume));
		double num = 0;
		int gg = 24;
		
		for(Flow f: list) {
			System.out.println(f.toString());
			num += f.getFlow();
		}
		System.out.println("Numero di rilevamenti: "+list.size()+
				"\nPortata media (SQL): "+dao.getAvgFlow(fiume)+
				"\nPortata media (Java): "+num/list.size());
		
		/*System.out.println("\nPortata media per il fiume \""+dao.getAllRivers().get(4).getName()+
				"\" nel giorno "+gg+": "+dao.getAvgFlowByDay(fiume, gg));
		System.out.println(dao.getAvgFlowByDay(fiume, gg));*/
		
	}

}
