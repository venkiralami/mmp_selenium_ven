package org.iitwf.heathcare.mmp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

public class MySqlConnectionManager {
    private static String url = "jdbc:mysql://localhost:3306/testDB";    
    private static String driverName = "com.mysql.cj.jdbc.Driver";   
    private static String username = "root";   
    private static String password = "admin";
    private static Connection con;

    public static void main(String[] args) {
        try {
            Class.forName(driverName);
            try {
                // Create connection
                con = DriverManager.getConnection(url, username, password);

                // Create scrollable ResultSet
                //Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                Statement stmt = con.createStatement();

                // ✅ Create table if it does not exist
                String createTableQuery = "CREATE TABLE IF NOT EXISTS employee ("
                                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                                        + "name VARCHAR(100) NOT NULL, "
                                        + "position VARCHAR(100) NOT NULL, "
                                        + "salary INT NOT NULL)";
                stmt.executeUpdate(createTableQuery);

                // ✅ Insert data (specify columns!)
                stmt.executeUpdate("INSERT INTO employee (name, position, salary) VALUES ('Venkat', 'Developer1', 65000)");
                // ✅ Select data
                ResultSet rs = stmt.executeQuery("SELECT * FROM employee");
                rs.last(); // Move to last row
                int rows = rs.getRow();
                if (rows == 0) {
                    System.out.println("No data found in the table.");
                    return;
                }
                ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
                int cols = rsmd.getColumnCount();
                System.out.println(rows + " : rows -- " + cols + " : columns");
                String[][] inputArr = new String[rows][cols];

                int i = 0;
                rs.beforeFirst();
                while (rs.next()) {
                    for (int j = 0; j < cols; j++) {
                        inputArr[i][j] = rs.getString(j + 1);
                        System.out.print(inputArr[i][j] + "\t");
                    }
                    System.out.println();
                    i++;
                }

                // ✅ Close connection
                rs.close();
                stmt.close();
                con.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Failed to create the database connection."); 
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("Driver not found."); 
        }
    }
}
