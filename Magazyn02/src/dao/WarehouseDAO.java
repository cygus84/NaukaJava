package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dataBase.DatabaseManager;
import model.Warehouse;

public class WarehouseDAO {

	public void createTable() {
		String sql = "CREATE TABLE IF NOT EXISTS warehouse (" + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "name TEXT NOT NULL" + ");";
		try (Connection conn = DatabaseManager.getConnection(); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addWarehouse(Warehouse warehouse) {
		String sql = "INSERT INTO warehouse(name) VALUES(?)";
		try (Connection conn = DatabaseManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, warehouse.getName());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Warehouse> getAllWarehouses() {
		List<Warehouse> list = new ArrayList<>();
		String sql = "SELECT * FROM warehouse";
		try (Connection conn = DatabaseManager.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				list.add(new Warehouse(rs.getInt("id"), rs.getString("name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
