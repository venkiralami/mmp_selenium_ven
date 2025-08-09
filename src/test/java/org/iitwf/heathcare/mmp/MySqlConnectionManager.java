package org.iitwf.heathcare.mmp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

public class MySqlConnectionManager
{
	private static String url = "jdbc:mysql://localhost:3306/testDB";    
	private static String driverName = "com.mysql.cj.jdbc.Driver";   
	private static String username = "root";   
	private static String password = "admin";
	private static Connection con;


	public static void main(String[] args) {

		try {
			Class.forName(driverName);
			try {
				//Create a connection to DB by passing Url,Username,Password as parameters
				con = DriverManager.getConnection(url, username, password);
				Statement stmt=con.createStatement();
				
				//Executing the Queries
				stmt.executeUpdate("INSERT INTO testDB.employee VALUES ('TOTALQA',25000)");
				ResultSet rs = stmt.executeQuery("Selet * from testDB.employee");
				//rs.last();
				int rows = rs.getRow();
				if (rows == 0) {
					System.out.println("No data found in the table.");
					return;
				}
				ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
				int cols = rsmd.getColumnCount();
				System.out.println(rows +"--" + cols);
				String[][] inputArr= new String[rows][cols];

				int i =0;
				rs.beforeFirst();
				//Iterating the data in the Table
				while (rs.next())
				{
					for(int j=0;j<cols;j++)
					{
						inputArr[i][j]=rs.getString(j+1);
						System.out.print(inputArr[i][j]);

					}
					System.out.println();
				}
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
