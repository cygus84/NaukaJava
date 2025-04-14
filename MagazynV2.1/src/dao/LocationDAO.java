package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Location;
import util.DatabaseManager;

public class LocationDAO {

	// Zwraca wszystkie lokalizacje z bazy danych
	public List<Location> getAll() {
		List<Location> locations = new ArrayList<>();
		String sql = "SELECT * FROM locations";

		try (Connection conn = DatabaseManager.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				Location location = new Location(rs.getInt("id"), rs.getString("name"));
				locations.add(location);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return locations;
	}

	// Zwraca lokalizację po ID
	public Location getById(int id) {
		String sql = "SELECT * FROM locations WHERE id = ?";
		Location location = null;

		try (Connection conn = DatabaseManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				location = new Location(rs.getInt("id"), rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return location;
	}

	// Dodaje nową lokalizację
	public void save(Location location) {
		String sql = "INSERT INTO locations (name) VALUES (?)";

		try (Connection conn = DatabaseManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, location.getName());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Aktualizuje lokalizację
	public void update(Location location) {
		String sql = "UPDATE locations SET name = ? WHERE id = ?";

		try (Connection conn = DatabaseManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, location.getName());
			pstmt.setInt(2, location.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Usuwa lokalizację
	public void delete(int id) {
		String sql = "DELETE FROM locations WHERE id = ?";

		try (Connection conn = DatabaseManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}