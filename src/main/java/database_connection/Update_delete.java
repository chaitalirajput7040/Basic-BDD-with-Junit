package database_connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Update_delete {

	private static String jdbcURL = 
			"jdbc:mysql://localhost:3306/online_banking_db?useSSL=false";
	
	private static String user = "root";
	private static String password = "chaitali@7040";
	
	private static boolean error = false;
	
	private static Connection connection = null; 
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	
	public static void main(String[] args) throws SQLException {
		
		try {
			connection = DriverManager.getConnection(jdbcURL, user, password);
			System.out.print("\nConnected to the database!");
			
			statement = connection.createStatement();
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			String[] qString = {
					"INSERT INTO customers " + 
							"VALUES('110', 'Ben', 'Adams', '1991-02-03','M')",
					"INSERT INTO customers " + 
							"VALUES('111', 'Ben', 'Bell', '1991-02-04','M')",
					"INSERT INTO customers " + 
							"VALUES('112', 'Ben', 'Frank', '1991-02-05','M')",
					"UPDATE customers SET lname='Green' WHERE cust_id=112"
			};
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 						
			// Executes the given SQL statement, which may be an INSERT, 
			// UPDATE, or DELETE statement or an SQL statement that 
			// returns nothing, such as an SQL DDL statement. 
			for (String qs : qString) {
				int rowsAffected = statement.executeUpdate(qs);
				// System.out.println("Number of rows affected = " + rowsAffected + "\n");	
			}
			
			resultSet = statement.executeQuery("select * from customers");
			displayCustomerResultset();	
			
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 				
			int rowsAffected = statement.executeUpdate(
					"DELETE from customers " + 
					"WHERE cust_id IN (110,111) OR lname='Green'");
			
			resultSet = statement.executeQuery("select * from customers");
			displayCustomerResultset();	
		}
		catch (Exception exception) {
			error = true;
			exception.printStackTrace();
		}
		finally {
			if(resultSet != null) 		resultSet.close();
			if(statement != null) 		statement.close();
			if(connection != null) 		connection.close();
			
			if(error)					System.out.println("\nFailed!");
			else						System.out.println("\nSuccess!");
		}
	}
	
	public static void displayCustomerResultset() throws SQLException {
		System.out.println("\n");
		
		while (resultSet.next()) {
			System.out.format("%-6s", resultSet.getInt("cust_id"));
			System.out.format("%-12s", resultSet.getString("fname"));
			System.out.format("%-12s", resultSet.getString("lname"));
			System.out.format("%-12s", resultSet.getDate("dob"));
			System.out.format("%-12s", resultSet.getString("gender"));
			
			System.out.format("%n");
		}   		
	}
}
