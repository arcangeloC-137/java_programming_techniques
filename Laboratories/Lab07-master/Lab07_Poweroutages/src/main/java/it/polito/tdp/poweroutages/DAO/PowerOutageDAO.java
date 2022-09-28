package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import it.polito.tdp.poweroutages.model.Blackout;
import it.polito.tdp.poweroutages.model.Nerc;

public class PowerOutageDAO {
	
	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	public List<Blackout> getBlackoutFromNERC(Nerc nerc) {

		String sql = "SELECT customers_affected, date_event_began,  date_event_finished, \r\n" + 
				"       TIMEDIFF(date_event_finished, date_event_began) AS blackoutHours\r\n" + 
				"FROM poweroutages \r\n" + 
				"WHERE nerc_id = ?";
		
		List<Blackout> listaBlackout = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, nerc.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				LocalDateTime dataInizio = res.getTimestamp("date_event_began").toLocalDateTime();
				LocalDateTime dataFine = res.getTimestamp("date_event_finished").toLocalDateTime();
				
				Blackout b = new Blackout(dataInizio, dataFine, res.getInt("customers_affected"));
				listaBlackout.add(b);
				
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		Collections.sort(listaBlackout, new comparatoreBlackout());
		
		return listaBlackout;
	}
}
