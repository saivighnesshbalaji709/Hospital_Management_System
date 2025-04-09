package util;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static String filename="db.properties";
	public static Connection getConnection() {
		Connection con=null;
		String constr=null;
		try {
			constr=PropertyUtil.getPropertyString(filename);
		}
		catch(IOException e){
			System.out.println("Failed to generate the connection string");	
		}
		if(constr!=null) { 
			   try { 
			    con=DriverManager.getConnection(constr); 
			   }catch (SQLException e) { 
			    System.out.println("Database connection failed!"); 
			    e.printStackTrace(); 
			} 
	     } 
	         return con; 
	}
}

