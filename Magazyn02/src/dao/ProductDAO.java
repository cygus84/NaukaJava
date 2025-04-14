package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

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
    
    public void update(Product product) {
        String sql = "UPDATE products SET name = ?, sku = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getSku());
            pstmt.setInt(3, product.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    
    public void delete(int productId) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Product getById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("sku"),
                    rs.getInt("location_id")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Zwróć null, jeśli nie znaleziono produktu
    }
    
    public void save(Product product) {
        String sql = "INSERT INTO products (name, sku, location_id) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getSku());
            pstmt.setInt(3, product.getCurrentLocationId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
