package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

	private static final String DATABASE_URL = "jdbc:sqlite:warehouse.db"; // Ścieżka do pliku bazy danych

    // Metoda do uzyskiwania połączenia z bazą danych
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    // Metoda do zamykania połączenia
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } 
}