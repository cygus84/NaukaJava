package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Location;
import util.DatabaseManager;

public class LocationDAO {
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
}
