package db;

import model.Medicine;
import java.sql.*;


public class InventoryManager {

    public void addMedicine(Medicine m) {
        String sql = "INSERT INTO inventory (name, price, stock, expiry_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseHandler.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, m.getName());
            pstmt.setDouble(2, m.getPrice());
            pstmt.setInt(3, m.getStock());
            pstmt.setString(4, m.getExpiryDate());
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void updateStock(int id, int newStock) {
        String sql = "UPDATE inventory SET stock = ? WHERE id = ?";
        try (Connection conn = DatabaseHandler.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newStock);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void deleteMedicine(int id) {
        String sql = "DELETE FROM inventory WHERE id = ?";
        try (Connection conn = DatabaseHandler.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public Medicine[] getAllMedicinesArray() {
    Medicine[] tempArray = new Medicine[100]; // Max capacity 100
    int count = 0;
    
    String sql = "SELECT * FROM inventory";
    try (Connection conn = DatabaseHandler.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
         
        while (rs.next() && count < 100) {
            Medicine m = new Medicine(
                rs.getString("name"), 
                rs.getDouble("price"), 
                rs.getInt("stock"), 
                rs.getString("expiry_date")
            );
            m.setId(rs.getInt("id"));
            tempArray[count] = m;
            count++;
        }
    } catch (SQLException e) { 
        e.printStackTrace(); 
    }
    
    return tempArray;
}
}