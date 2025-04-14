package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Movement;
import util.DatabaseManager;

public class MovementDAO {

    // Zapisuje nowy ruch do tabeli movements
    public void save(Movement movement) {
        String sql = "INSERT INTO movements (product_id, old_location_id, new_location_id, timestamp) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, movement.getProductId());
            pstmt.setInt(2, movement.getOldLocationId());
            pstmt.setInt(3, movement.getNewLocationId());
            pstmt.setTimestamp(4, Timestamp.valueOf(movement.getTimestamp()));
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Pobiera wszystkie ruchy produktu na podstawie jego ID
    public List<Movement> getByProductId(int productId) {
        List<Movement> movements = new ArrayList<>();
        String sql = "SELECT * FROM movements WHERE product_id = ? ORDER BY timestamp DESC";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int oldLocationId = rs.getInt("old_location_id");
                int newLocationId = rs.getInt("new_location_id");
                LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();

                Movement movement = new Movement(
                        productId,
                        oldLocationId,
                        newLocationId,
                        timestamp
                );
                movements.add(movement);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movements;
    }
}
