package org.iitwf.heathcare.mmp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQLSample {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/testDB";
        String user = "root";  // change to your username
        String password = "admin"; // change to your password

        try {
            // 1. Connect to MySQL
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connected to MySQL!");

            // 2. Create a table if it doesn't exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS employee (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50), " +
                    "position VARCHAR(50),salary INTEGER)";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(createTableSQL);

            // 3. Insert sample data
            String insertSQL = "INSERT INTO employee (name, position, salary) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);
            pstmt.setString(1, "Alice");
            pstmt.setString(2, "Developer");
            pstmt.setInt(3, 10000);
            pstmt.executeUpdate();

            pstmt.setString(1, "Bob");
            pstmt.setString(2, "Manager");
            pstmt.setInt(3, 50000);
            pstmt.executeUpdate();

            System.out.println("✅ Data inserted! Check the database.");

            // 4. Read data
            ResultSet rs = stmt.executeQuery("SELECT * FROM employee");
            while (rs.next()) {
            	
                System.out.println(rs.getInt("id") + " | " +
                                   rs.getString("name") + " | " +
                                   rs.getString("position") + " | " +
                				   rs.getInt("salary"));
            }

            // 5. Close connection
            conn.close();
            System.out.println("✅ Connection closed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

