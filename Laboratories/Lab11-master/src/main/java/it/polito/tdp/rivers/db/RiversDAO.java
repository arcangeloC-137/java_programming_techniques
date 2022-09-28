package it.polito.tdp.rivers.db;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public class RiversDAO {

	public List<River> getAllRivers() {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				rivers.add(new River(res.getInt("id"), res.getString("name")));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}
	
	public List<Flow> getRilevazioniFiume(River fiume){
		
		final String sql = "SELECT DATE(DAY) as gg, flow " + 
				"FROM flow " + 
				"WHERE river = ?";
		
		List<Flow> rilevamenti = new LinkedList<>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, fiume.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				LocalDate data = res.getDate("gg").toLocalDate();
				rilevamenti.add(new Flow(data, res.getDouble("flow"), fiume));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rilevamenti;
	}
	
	public double getAvgFlow(River fiume) {
		final String sql ="SELECT AVG(flow) as avg " + 
				"FROM flow " + 
				"WHERE river = ?";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, fiume.getId());
			ResultSet res = st.executeQuery();

			res.next();
			double num = res.getDouble("avg");
			conn.close();
			return num;
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

	}
	
	public Map<Integer, Double> getAvgFlowByDay(River fiume) {
		final String sql ="SELECT AVG(f1.flow) AS avg, DAY(f1.day) AS gg " + 
				"FROM flow AS f1, flow AS f2 " + 
				"WHERE f1.river = ? AND f1.day=f2.day " + 
				"GROUP BY DAY(f2.day), DAY(f1.day)";
		Map<Integer, Double> mappa = new HashMap<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, fiume.getId());
			ResultSet res = st.executeQuery();

			while(res.next()) {
				mappa.put(res.getInt("gg"), res.getDouble("avg"));
			}
			conn.close();
			return mappa;
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

	}
}
