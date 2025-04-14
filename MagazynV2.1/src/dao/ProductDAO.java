package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Product;
import util.DatabaseManager;

public class ProductDAO {
	public void save(Product product) {
	    String sql = "INSERT INTO products (name, sku, location_id) VALUES (?, ?, ?)";

	    try (Connection conn = DatabaseManager.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

	        pstmt.setString(1, product.getName());
	        pstmt.setString(2, product.getSku());
	        pstmt.setInt(3, product.getCurrentLocationId());
	        pstmt.executeUpdate();

	        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                product.setId(generatedKeys.getInt(1));
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public void update(Product product) {
		String sql = "UPDATE products SET name = ?, sku = ?, location_id = ? WHERE id = ?";

		try (Connection conn = DatabaseManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, product.getName());
			pstmt.setString(2, product.getSku());
			pstmt.setInt(3, product.getCurrentLocationId());
			pstmt.setInt(4, product.getId());
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

	public List<Product> getAll() {
		List<Product> products = new ArrayList<>();
		String sql = "SELECT * FROM products";

		try (Connection conn = DatabaseManager.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				Product product = new Product(rs.getInt("id"), rs.getString("name"), rs.getString("sku"),
						rs.getInt("location_id"));
				products.add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return products;
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

	    return null;
	}
	
	   public void updateLocation(Product product, int newLocationId) {
	        String sql = "UPDATE products SET location_id = ? WHERE id = ?";

	        try (Connection conn = DatabaseManager.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            pstmt.setInt(1, newLocationId);
	            pstmt.setInt(2, product.getId());
	            pstmt.executeUpdate();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
}
