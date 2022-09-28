package it.polito.anagrammi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DizionarioDAO {

	public boolean getParolaDaDizionario(String parola){
		final String sql = "SELECT nome FROM parola WHERE nome = ?";
		String risultato = null;
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, parola);
			ResultSet rs =st.executeQuery();
			while (rs.next()) {
				risultato = rs.getString("nome");
			}
			conn.close();
			
		}catch(SQLException e){
			
			throw new RuntimeException("Errore Db", e);
		}
		
		if(risultato!=null) {
			return true;
		}
		return false;
		
	}
}
