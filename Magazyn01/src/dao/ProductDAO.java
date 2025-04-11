package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dataBase.DatabaseManager;
import model.Product;

public class ProductDAO {
	 // Metoda do dodawania produktu
    public void insert(Product product) {
        String sql = "INSERT INTO products(name, sku, location_id) VALUES(?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getSku());
            pstmt.setInt(3, product.getCurrentLocationId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metoda do pobierania wszystkich produktów
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection conn = DatabaseManager.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String sku = rs.getString("sku");
                int locationId = rs.getInt("location_id");
                products.add(new Product(id, name, sku, locationId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
}
