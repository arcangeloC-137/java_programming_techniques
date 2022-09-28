package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Condiment;

public class FoodDAO {

	public List<Food> listAllFood(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_id"),
							res.getInt("food_code"),
							res.getString("display_name"), 
							res.getInt("portion_default"), 
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"),
							res.getDouble("calories")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}

	}
	
	public void listAllCondiment(Map<Integer,Condiment> idMap){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					idMap.put(res.getInt("food_code"), new Condiment(res.getInt("condiment_id"),
							res.getInt("food_code"),
							res.getString("display_name"), 
							res.getString("condiment_portion_size"), 
							res.getDouble("condiment_calories")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return  ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return  ;
		}

	}
	
	public List<Condiment> listCondimentByCalories(double calories){
		
		String sql = "SELECT DISTINCT * " + 
				"FROM condiment AS c " + 
				"WHERE c.condiment_calories<? " + 
				"ORDER BY c.display_name " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setDouble(1, calories);
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_id"),
							res.getInt("food_code"),
							res.getString("display_name"), 
							res.getString("condiment_portion_size"), 
							res.getDouble("condiment_calories")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}

	}
	
	public void addEdges(Graph<Condiment, DefaultWeightedEdge> grafo, Map<Integer,Condiment> idMap) {

		String sql = "SELECT f1.condiment_food_code AS cod1, f2.condiment_food_code AS cod2, COUNT(f1.condiment_food_code) AS peso " + 
				"FROM food_condiment AS f1, food_condiment AS f2 " + 
				"WHERE f1.condiment_food_code < f2.condiment_food_code AND " + 
				"f1.food_code = f2.food_code " + 
				"GROUP BY f1.condiment_food_code ";
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet res = st.executeQuery();

			while (res.next()) {
				try {
					
					Condiment v1 = idMap.get(res.getInt("cod1"));
					Condiment v2 = idMap.get(res.getInt("cod2"));
					
					if(grafo.containsVertex(v1) && grafo.containsVertex(v2)) {
						Graphs.addEdge(grafo, v1, v2, res.getInt("peso"));
					}
					
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}

			conn.close();
			return ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ;
		}

	}
}

