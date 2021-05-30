package database_connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;	//Note
import java.sql.ResultSet;
import java.sql.SQLException;


public class Prepared_Statement {
	private static String jdbcURL = 
			"jdbc:mysql://localhost:3306/online_banking_db?useSSL=false";
	
	private static String user = "root";
	private static String password = "chaitali@7040";
	
	private static boolean error = false;
	
	private static Connection connection = null; 
	private static PreparedStatement prepStatement = null;	//Note
	private static ResultSet resultSet = null;
	
	public static void main(String[] args) throws SQLException {

		try {			
			connection = DriverManager.getConnection(jdbcURL, user, password);
			System.out.print("\nConnected to the database!");
			
			// statement = connection.createStatement();
			testPreparedStatements();
			
		}
		catch (Exception exception) {
			error = true;
			exception.printStackTrace();
		}
		finally {
			if(resultSet != null) 			resultSet.close();
			if(prepStatement != null) 		prepStatement.close();	//Note
			if(connection != null) 			connection.close();
			
			if(error)						System.out.println("\nFailed!");
			else							System.out.println("\nSuccess!");
		}
	}
	
	public static void testPreparedStatements() throws SQLException {
		System.out.println();
		
		// Creates a PreparedStatement object for sending 
		// parameterized SQL statements to the database. 
		prepStatement = connection.prepareStatement(
				"SELECT * FROM customers WHERE cust_id=? AND fname=?");	

		prepStatement.setInt(1, 101);
		prepStatement.setString(2, "Eva");	
		
		resultSet = prepStatement.executeQuery();
		displayCustomerResultset();
		
		// - - - - - - - - - - 
		prepStatement.setInt(1, 105);
		prepStatement.setString(2, "Daisy");
		
		resultSet = prepStatement.executeQuery();
		
		displayCustomerResultset();
		
		// - - - - - - - - - - 		
		prepStatement = connection.prepareStatement(
				"SELECT * FROM customers " + 
				"WHERE cust_id BETWEEN ? AND ? OR cust_id = ?");
		
		prepStatement.setInt(1, 107);
		prepStatement.setInt(2, 109);
		prepStatement.setInt(3, 102);
		
		resultSet = prepStatement.executeQuery();
		displayCustomerResultset();
		
	}
	
	public static void displayCustomerResultset() throws SQLException {
		System.out.println();
		
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
