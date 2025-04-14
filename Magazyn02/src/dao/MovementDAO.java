package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dataBase.DatabaseManager;
import model.Movement;

public class MovementDAO {

	/**
	 * Przenosi produkt do nowej lokalizacji.
	 * 
	 * @param productId     ID produktu, który ma zostać przeniesiony
	 * @param newLocationId ID nowej lokalizacji
	 */
	public void moveProduct(int productId, int newLocationId) {
		String sql = "UPDATE products SET location_id = ? WHERE id = ?";

		try (Connection conn = DatabaseManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, newLocationId);
			pstmt.setInt(2, productId);

			int updated = pstmt.executeUpdate();

			if (updated == 0) {
				System.out.println("Nie znaleziono produktu o ID: " + productId);
			}

		} catch (SQLException e) {
			System.err.println("Błąd przy przenoszeniu produktu: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
