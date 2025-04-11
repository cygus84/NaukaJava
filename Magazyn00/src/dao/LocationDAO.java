package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dataBase.DatabaseManager;
import model.Location;

public class LocationDAO {
	public void createTable() {
		String sql = "CREATE TABLE IF NOT EXISTS location (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "warehouse_id INTEGER NOT NULL, " + "rack TEXT NOT NULL, " + "shelf TEXT NOT NULL, "
				+ "FOREIGN KEY (warehouse_id) REFERENCES warehouse(id)" + ");";
		try (Connection conn = DatabaseManager.getConnection(); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insert(Location location) {
		String sql = "INSERT INTO location (warehouse_id, rack, shelf) VALUES (?, ?, ?)";
		try (Connection conn = DatabaseManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, location.getWarehouseId());
			pstmt.setString(2, location.getRack());
			pstmt.setString(3, location.getShelf());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Location> getAll() {
		List<Location> list = new ArrayList<>();
		String sql = "SELECT * FROM location";
		try (Connection conn = DatabaseManager.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				list.add(new Location(rs.getInt("id"), rs.getInt("warehouse_id"), rs.getString("rack"),
						rs.getString("shelf")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
