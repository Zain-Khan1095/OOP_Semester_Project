package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHandler {
  
    private static final String URL = "jdbc:mysql://localhost:3306/pharmacy_db";
    private static final String USER = "root"; 
    private static final String PASS = "1234567890-=";    

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found!");
        }
    }

    public static void setupDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS inventory (" +
                     "id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                     "name VARCHAR(255) NOT NULL, " +
                     "price DOUBLE NOT NULL, " +
                     "stock INTEGER NOT NULL, " +
                     "expiry_date VARCHAR(50)" +
                     ");";

        try (Connection conn = getConnection(); 
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("MySQL Database connected and table ready.");
        } catch (SQLException e) {
            System.err.println("MySQL Setup Error: " + e.getMessage());
            System.err.println("Make sure MySQL is running and 'pharmacy_db' exists!");
        }
    }
}