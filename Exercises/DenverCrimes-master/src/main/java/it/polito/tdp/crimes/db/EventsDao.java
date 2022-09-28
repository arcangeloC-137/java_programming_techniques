package it.polito.tdp.crimes.db;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import it.polito.tdp.crimes.model.Adiacenze;
import it.polito.tdp.crimes.model.Event;


public class EventsDao {
	
	public void listAllEvents(Map <Long, Event> idMap){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					idMap.put(res.getLong("incident_id"), new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
	}

	public List<String> getCategorie() {
		String sql = "SELECT DISTINCT offense_category_id " + 
				"FROM events";

		List<String> list = new ArrayList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(res.getString("offense_category_id"));
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}
	
	public List<Integer> getMesi() {
		String sql = "SELECT DISTINCT MONTH(reported_date) as mese " + 
				"FROM events";
		
		List<Integer> mesi = new ArrayList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				
				mesi.add(res.getInt("mese"));
				
			}
			
			Collections.sort(mesi);
			conn.close();
			return mesi ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}

	//SIMILE A getAllVertices creato da me, ma in forma più smart
	public List<Adiacenze> getAdiacenze(String categoryCrime, int mese) {
		String sql = "SELECT e1.offense_type_id AS v1, e2.offense_type_id AS v2, COUNT(DISTINCT e2.neighborhood_id) AS num\n " + 
				"FROM EVENTS AS e1, events AS e2\n " + 
				"WHERE e1.offense_category_id = ? AND " + 
				"e2.offense_category_id= ? AND  " + 
				"MONTH(e1.reported_date)=? AND " + 
				"MONTH(e2.reported_date)=? AND " + 
				"e1.offense_type_id != e2.offense_type_id AND " + 
				"e1.neighborhood_id = e2.neighborhood_id " + 
				"GROUP BY e1.offense_type_id, e2.offense_type_id ";
		
		List<Adiacenze> list = new ArrayList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setString(1, categoryCrime);
			st.setString(2, categoryCrime);
			st.setInt(3, mese);
			st.setInt(4, mese);
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				
				list.add(new Adiacenze(res.getString("v1"),res.getString("v2"),res.getInt("num")));
				
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}
	
	/*
	public List<String> getAllVertices(String categoryId, Month mese) {
		
		String sql = "SELECT DISTINCT offense_type_id " + 
				"FROM EVENTS " + 
				"WHERE offense_category_id = ? " + 
				"AND MONTH(reported_date) = ?";
		
		List<String> list = new ArrayList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setString(1, categoryId);
			st.setInt(2, mese.getValue());
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				
				list.add(res.getString("offense_type_id"));
				
			}
			
			Collections.sort(list);
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}
	
	public int getNumeroQuartieri(String reato1, String reato2) {
		
		String sql = "SELECT COUNT(DISTINCT neighborhood_id) AS num " + 
				"FROM EVENTS " + 
				"WHERE offense_type_id=? OR offense_type_id=?";
		
		int numeroQuartieri = 0;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setString(1, reato1);
			st.setString(2, reato2);
			
			ResultSet res = st.executeQuery();
			res.next();
			
			conn.close();
			return res.getInt("num") ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	*/
}
