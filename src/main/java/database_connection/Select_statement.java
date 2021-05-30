package database_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Select_statement {
	private static String jdbcURL  = "jdbc:mysql://localhost:3306/online_fruit_and_veggies?useSSL=false";
	private static String user     = "root";
private static	String password = "chaitali@7040";
	
	private static boolean error=false;
	
	private static Connection  connection = null;
	private static Statement statement=null;
	private static ResultSet resultset=null;
	
	
	
	public static List<String> Get_data() throws SQLException{
		List<String> result = new ArrayList<String>();
		try {
			connection = DriverManager.getConnection(jdbcURL, user, password);
			System.out.println("\\n Connected to database!");
			
			statement=connection.createStatement();
			resultset=statement.executeQuery("select * from user");
			while(resultset.next())
			{
				result.add(resultset.getString(1));
				result.add(resultset.getString(2));
				
			//ystem.out.format("%-12s", resultset.getString("uname"));
			//ystem.out.format("%-12s", resultset.getString("pass"));

			//ystem.out.format("%n");
			}
			
	
			
		}
		catch (Exception exception)
		{
			error=true;
			exception.printStackTrace();
		}
		finally
		{
if(resultset!= null) 		resultset.close();
			if(statement != null) 		statement.close();
			if(connection != null) 		connection.close();
			
			if(error)					System.out.println("\nFailed!");
			else                        System.out.println("\nSuccess!");
		}
		
		
		return result;
			}

	
	
	

	
	

}
