package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
	 private static final String DATABASE_URL = "jdbc:sqlite:warehouse.db";  // Ścieżka do Twojej bazy SQLite
	    private static final String JDBC_DRIVER = "org.sqlite.JDBC";  // Driver JDBC dla SQLite

	    // Metoda statyczna do uzyskania połączenia z bazą danych
	    public static Connection getConnection() throws SQLException {
	        try {
	            // Załaduj sterownik JDBC
	            Class.forName(JDBC_DRIVER);
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	            throw new SQLException("Nie udało się załadować sterownika JDBC dla SQLite.");
	        }

	        // Zwróć połączenie do bazy danych SQLite
	        return DriverManager.getConnection(DATABASE_URL);
	    }
}
