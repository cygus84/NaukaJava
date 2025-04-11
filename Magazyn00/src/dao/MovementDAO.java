package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dataBase.DatabaseManager;
import model.Movement;

public class MovementDAO {

	private final ProductDAO productDAO = new ProductDAO();

	public void createTable() {
		String sql = "CREATE TABLE IF NOT EXISTS movement (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "product_id INTEGER NOT NULL, " + "from_location_id INTEGER, " + "to_location_id INTEGER NOT NULL, "
				+ "moved_at DATETIME DEFAULT CURRENT_TIMESTAMP, " + "FOREIGN KEY (product_id) REFERENCES product(id), "
				+ "FOREIGN KEY (from_location_id) REFERENCES location(id), "
				+ "FOREIGN KEY (to_location_id) REFERENCES location(id)" + ");";
		try (Connection conn = DatabaseManager.getConnection(); Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insert(Movement movement) {
		String sql = "INSERT INTO movement (product_id, from_location_id, to_location_id, moved_at) VALUES (?, ?, ?, ?)";
		try (Connection conn = DatabaseManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, movement.getProductId());

			if (movement.getFromLocationId() != null) {
				pstmt.setInt(2, movement.getFromLocationId());
			} else {
				pstmt.setNull(2, Types.INTEGER);
			}

			pstmt.setInt(3, movement.getToLocationId());
			pstmt.setString(4, movement.getMovedAt().toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void moveProduct(int productId, int toLocationId) {
		try {
			ProductDAO productDAO = new ProductDAO();
			List<model.Product> products = productDAO.getAll();
			Integer currentLocationId = null;

			for (model.Product p : products) {
				if (p.getId() == productId) {
					currentLocationId = p.getCurrentLocationId();
					break;
				}
			}

			insert(new Movement(productId, currentLocationId, toLocationId));
			productDAO.updateLocation(productId, toLocationId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Movement> getAll() {
		List<Movement> list = new ArrayList<>();
		String sql = "SELECT * FROM movement";
		try (Connection conn = DatabaseManager.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				list.add(new Movement(rs.getInt("id"), rs.getInt("product_id"),
						rs.getObject("from_location_id", Integer.class), rs.getInt("to_location_id"),
						rs.getTimestamp("moved_at").toLocalDateTime()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
