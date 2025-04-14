package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Location;
import util.DatabaseManager;

public class LocationDAO {
	
	String newLocationText;
	public void save(Location location) {
		String sql = "INSERT INTO locations (name) VALUES (?)";

		try (Connection conn = DatabaseManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			pstmt.setString(1, location.getName());
			pstmt.executeUpdate();

			try (ResultSet keys = pstmt.getGeneratedKeys()) {
				if (keys.next()) {
					location.setId(keys.getInt(1));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Location> getAll() {
		List<Location> locations = new ArrayList<>();
		String sql = "SELECT * FROM locations";

		try (Connection conn = DatabaseManager.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				locations.add(new Location(rs.getInt("id"), rs.getString("name")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return locations;
	}
	
	// Zakładamy, że newLocationField to JTextField, w którym użytkownik wpisuje ID lokalizacji
 newLocationText = newLocationField.getText();

	// Sprawdzamy, czy użytkownik wpisał liczbę
	int newLocationId;
	try {
	    newLocationId = Integer.parseInt(newLocationText1);  // Przekształcamy tekst na liczbę całkowitą
	} catch (NumberFormatException ex) {
	    JOptionPane.showMessageDialog(this, "Niepoprawny format ID lokalizacji. Proszę podać liczbę całkowitą.", "Błąd", JOptionPane.ERROR_MESSAGE);
	    return;
	}

	// Teraz możesz używać newLocationId (ID nowej lokalizacji)
}
