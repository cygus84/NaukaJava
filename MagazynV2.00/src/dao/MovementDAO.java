package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Movement;
import util.DatabaseManager;

public class MovementDAO {
	public void save(Movement movement) {
		String sql = "INSERT INTO movements (product_id, from_location_id, to_location_id, timestamp) VALUES (?, ?, ?, ?)";

		try (Connection conn = DatabaseManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, movement.getProductId());
			pstmt.setInt(2, movement.getFromLocationId());
			pstmt.setInt(3, movement.getToLocationId());
			pstmt.setString(4, movement.getTimestamp().toString());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Movement> getAll() {
		List<Movement> movements = new ArrayList<>();
		String sql = "SELECT * FROM movements";

		try (Connection conn = DatabaseManager.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				movements.add(new Movement(rs.getInt("id"), rs.getInt("product_id"), rs.getInt("from_location_id"),
						rs.getInt("to_location_id"), LocalDateTime.parse(rs.getString("timestamp"))));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return movements;
	}
}
