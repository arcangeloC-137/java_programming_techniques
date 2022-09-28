package it.polito.tdp.seriea.db;

import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;

public class SerieADAO {

	public List<Season> listAllSeasons(Map<String, Season> idMapSeason) {
		String sql = "SELECT season, description FROM seasons ";
		List<Season> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Season stagione = new Season(res.getInt("season"), res.getString("description"));
				result.add(stagione);
				idMapSeason.put(res.getString("description"), stagione);
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Team> listTeams() {
		String sql = "SELECT team FROM teams";
		List<Team> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Team(res.getString("team")));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void getRisultatiAnnate(Team t, Map<Season, Integer> mappaAnnoPunteggio, Map<String, Season> idMapSeason) {
		String sql = "SELECT s.description AS anno, COUNT(m.FTR) AS pt, m.FTR AS tipo "
				+ "FROM seasons AS s, teams AS t, matches AS m " + "WHERE t.team=? AND s.season = m.Season AND "
				+ "((m.HomeTeam=t.team AND m.FTR='H') OR (m.AwayTeam=t.team AND m.FTR='A') OR "
				+ "((m.HomeTeam=t.team OR m.AwayTeam=t.team) AND m.FTR='D')) "
				+ "GROUP BY s.season, m.FTR='H', m.FTR='D', m.FTR='A' ";

		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, t.getTeam());
			ResultSet res = st.executeQuery();

			while (res.next()) {

				String tipo = res.getString("tipo");
				Season stagione = idMapSeason.get(res.getString("anno"));

				if (tipo.equals("H") || tipo.equals("A")) {
					int risultato = res.getInt("pt")*3;
					mappaAnnoPunteggio.replace(stagione, mappaAnnoPunteggio.get(stagione)+risultato);
				}else {
					int risultato = res.getInt("pt");
					mappaAnnoPunteggio.replace(stagione, mappaAnnoPunteggio.get(stagione)+risultato);
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

}

/*
 * 
 */
