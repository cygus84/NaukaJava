package dataBase;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitializer {
	  public static void initializeDatabase() {
	        try (Connection conn = DatabaseManager.getConnection(); Statement stmt = conn.createStatement()) {
	            // Tworzenie tabeli produktów
	            String createProductsTable = "CREATE TABLE IF NOT EXISTS products (" +
	                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
	                    "name TEXT NOT NULL, " +
	                    "sku TEXT NOT NULL, " +
	                    "location_id INTEGER NOT NULL, " +
	                    "FOREIGN KEY(location_id) REFERENCES locations(id)" +
	                    ");";
	            stmt.execute(createProductsTable);

	            // Tworzenie tabeli lokalizacji
	            String createLocationsTable = "CREATE TABLE IF NOT EXISTS locations (" +
	                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
	                    "warehouse_id INTEGER NOT NULL, " +
	                    "rack TEXT NOT NULL, " +
	                    "shelf TEXT NOT NULL" +
	                    ");";
	            stmt.execute(createLocationsTable);

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
}
