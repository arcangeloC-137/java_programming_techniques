package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Blackout;
import it.polito.tdp.poweroutages.model.Nerc;

public class TestPowerOutagesDAO {

	public static void main(String[] args) {
		
		try {
			Connection connection = ConnectDB.getConnection();
			connection.close();
			System.out.println("Connection Test PASSED");
			
			PowerOutageDAO dao = new PowerOutageDAO() ;
			
			//System.out.println(dao.getNercList()) ;

		} catch (Exception e) {
			System.err.println("Test FAILED");
		}
		
		try {
			Connection connection = ConnectDB.getConnection();
			connection.close();
			System.out.println("Connection Test PASSED");
			
			PowerOutageDAO dao = new PowerOutageDAO();
			List<Blackout> temp = new ArrayList<>(dao.getBlackoutFromNERC(new Nerc(2, "MAAC")));
			
			
			for(Blackout b: temp) {
				System.out.print(b);
				//System.out.println("Ultimo anno: "+b.getData_fine().getYear()+" Primo Anno: "+b.getData_inizio().getYear());
			}
			
		
		} catch (Exception e) {
			System.err.println("Test FAILED");
		}

	}

}
