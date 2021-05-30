package database_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
	
public static void main(String[] args) throws SQLException {
		
		Connection connection = null; 
		
		String jdbcURL  = "jdbc:mysql://localhost:3306/online_banking_db?useSSL=false";
		String user     = "root";
		String password = "chaitali@7040";
		
		boolean error = false;
		
		try {
			connection = DriverManager.getConnection(jdbcURL, user, password);
			System.out.print("\nConnected to the database!");
		}
		catch (Exception exception) {
			error = true;
			exception.printStackTrace();
		}
		finally {
			if(connection != null) 			connection.close();
			
			if(error)	System.out.println("\nFailed!");
			else		System.out.println("\nSuccess!");
		}
	}

}
