package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dataBase.DatabaseManager;
import model.Location;

public class LocationDAO {
	 // Metoda do dodawania lokalizacji
    public void insert(Location location) {
        String sql = "INSERT INTO locations(warehouse_id, rack, shelf) VALUES(?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, location.getWarehouseId());
            pstmt.setString(2, location.getRack());
            pstmt.setString(3, location.getShelf());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metoda do pobierania wszystkich lokalizacji
    public List<Location> getAll() {
        List<Location> locations = new ArrayList<>();
        String sql = "SELECT * FROM locations";

        try (Connection conn = DatabaseManager.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int warehouseId = rs.getInt("warehouse_id");
                String rack = rs.getString("rack");
                String shelf = rs.getString("shelf");
                locations.add(new Location(id, warehouseId, rack, shelf));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return locations;
    }
}
