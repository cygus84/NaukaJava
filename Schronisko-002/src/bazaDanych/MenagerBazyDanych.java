package bazaDanych;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MenagerBazyDanych {

	private static MenagerBazyDanych bd = null; // statyczna zmienna musi miec wartosc
	private Connection polaczenie;
	
	private MenagerBazyDanych() {
		String jdbcURL =  "jdbc:sqlite:baza.db";
		try{
			polaczenie = DriverManager.getConnection(jdbcURL); // nawiazanie polaczenia
			sprawdzenieBazyDanych();
		} catch(SQLException e) {
			System.out.println("Problem z polaczenie z baza danych: " + e.getMessage().toString());
			System.exit(1);
		}
	}

	private void sprawdzenieBazyDanych() {
		updateDB("CREATE TABLE IF NOT EXISTS koty(id INT IDENTITY(1, 1) PRIMARY KEY, nazwa VARCHAR(20));");
	}
	
	public void updateDB(String sql) {
		Statement st;
		try {
			st = polaczenie.createStatement();
			st.executeUpdate(sql);
		} catch(SQLException e) {
			System.err.println("Problem z akutlizacja bazy danych! " + e.getMessage().toString());
			System.exit(1);
		}
	}
	
	public ResultSet getData(String sql) {
		ResultSet rs = null; // bezpiecznik
		Statement st;
		try {
			st = polaczenie.createStatement();
			rs = st.executeQuery(sql);
		} catch(SQLException e) {
			System.err.println("Problem z pobraniem bazy danych! " + e.getMessage().toString());
			System.exit(1);
		}
		return rs;
	}
	
	public static MenagerBazyDanych getInstance() {
		if(bd == null) {
			bd = new MenagerBazyDanych();
		}
		return bd;
	}
	
}
