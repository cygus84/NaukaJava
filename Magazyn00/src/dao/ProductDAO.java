package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dataBase.DatabaseManager;
import model.Product;

public class ProductDAO {
	public void createTable() {
		String sql = "CREATE TABLE IF NOT EXISTS product (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "name TEXT NOT NULL, " + "sku TEXT UNIQUE NOT NULL, " + "current_location_id INTEGER, "
				+ "FOREIGN KEY (current_location_id) REFERENCES location(id)" + ");";
		try (Connection conn = DatabaseManager.getConnection(); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insert(Product product) {
		String sql = "INSERT INTO product (name, sku, current_location_id) VALUES (?, ?, ?)";
		try (Connection conn = DatabaseManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, product.getName());
			pstmt.setString(2, product.getSku());
			if (product.getCurrentLocationId() != null) {
				pstmt.setInt(3, product.getCurrentLocationId());
			} else {
				pstmt.setNull(3, Types.INTEGER);
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Product> getAll() {
		List<Product> list = new ArrayList<>();
		String sql = "SELECT * FROM product";
		try (Connection conn = DatabaseManager.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				list.add(new Product(rs.getInt("id"), rs.getString("name"), rs.getString("sku"),
						rs.getObject("current_location_id", Integer.class)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void updateLocation(int productId, int newLocationId) {
		String sql = "UPDATE product SET current_location_id = ? WHERE id = ?";
		try (Connection conn = DatabaseManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, newLocationId);
			pstmt.setInt(2, productId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
