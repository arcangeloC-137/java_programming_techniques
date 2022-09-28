package it.polito.tdp.seriea.db;

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

import it.polito.tdp.seriea.model.Match;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;

public class SerieADAO {

	public List<Season> listSeasons() {
		String sql = "SELECT season, description FROM seasons ";
		List<Season> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Season(res.getInt("season"), res.getString("description")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Team> listTeams(Map<String, Team> idMap) {
		String sql = "SELECT team FROM teams";
		List<Team> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Team(res.getString("team")));
				idMap.put( res.getString("team"), new Team(res.getString("team")));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void riempiGrafo(Graph<Team, DefaultWeightedEdge> grafo, Map<String,Team> idMap) {

		
		String sql = "SELECT t1.team, t2.team, COUNT(t1.team) AS peso " + 
				"FROM matches AS m, teams AS t1, teams AS t2 " + 
				"WHERE t1.team < t2.team AND " + 
				"(m.HomeTeam=t1.team AND m.AwayTeam=t2.team) " + 
				"GROUP BY t1.team, t2.team ";
		
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				Team t1 = idMap.get(res.getString("t1.team"));
				Team t2 = idMap.get(res.getString("t2.team"));
				
				if(grafo.containsVertex(t1) && grafo.containsVertex(t2)) {
					Graphs.addEdge(grafo, t1, t2, res.getInt("peso")*2);
				}else {
					if(!grafo.containsVertex(t1) && grafo.containsVertex(t2)) {
						grafo.addVertex(t1);
						Graphs.addEdge(grafo, t1, t2, res.getInt("peso")*2);
					}else if(!grafo.containsVertex(t2) && grafo.containsVertex(t1)) {
						grafo.addVertex(t2);
						Graphs.addEdge(grafo, t1, t2, res.getInt("peso")*2);
					}else {
						grafo.addVertex(t2);
						grafo.addVertex(t1);
						Graphs.addEdge(grafo, t1, t2, res.getInt("peso")*2);
					}
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

	public void getSquadreCampionato(Season value, List<Team> listaSquadreCampionato, Map<String, Team> idMap) {
		// TODO Auto-generated method stub
		String sql = "SELECT DISTINCT m.HomeTeam AS sq " + 
				"FROM matches AS m " + 
				"WHERE m.Season=? ";
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, value.getDescription());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				listaSquadreCampionato.add(idMap.get(res.getString("sq")));
			}

			conn.close();
			return ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ;
		}
	}

	public void getListaIncontriStagione(Season value, List<Match> listaIncontri, Map<String, Team> idMap) {
		String sql = "SELECT * " + 
				"FROM matches AS m " + 
				"WHERE m.Season=? "
				+ "ORDER BY m.Date ";
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, value.getDescription());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				listaIncontri.add(new Match(
						res.getInt("match_id"), value, res.getString("Div"), res.getDate("Date").toLocalDate(), 
						idMap.get(res.getString("HomeTeam")), idMap.get(res.getString("AwayTeam")), 
						res.getInt("FTHG"), res.getInt("FTAG"), res.getString("FTR")));
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

