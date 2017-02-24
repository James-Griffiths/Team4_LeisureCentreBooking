package Leisure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class leisure_DB {

	//sql
	final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	final String DB_URL = "jdbc:mysql://localhost:3306/Leisure?useSSL=false";
	final String USER_NAME = "root";
	final String PASSWORD = "password";
	Connection conn = null;
	
	public leisure_DB(){
	
	try{
		Class.forName(JDBC_DRIVER);
		System.out.println("Driver Registered");
		
		// STEP 2 - Open a connection
					//          Use the DriverManager.getConnection() method to create a Connection object,
					//          which represents a physical connection with the database server.
					conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
					System.out.println("Connected");
	}
	catch (ClassNotFoundException cnfe){
		System.out.println("Could not load driver.\n" + cnfe.getMessage());
	}
	catch (SQLException e) {
		System.out.println("Problem with SQL.\n" + e.getMessage());
	}
	/*finally{
		try{
			if(conn != null){
			conn.close();
		    System.out.println("Connection closed.");
			}
		}
		catch (Exception e){
			System.out.println("Could not close connection.\n" + e.getMessage());
		}
	}*/
	}
	
	
	public String queryAllAreas(){
		try{
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	    System.out.println("Statement object created");
	    
	    StringBuilder full = new StringBuilder();
	    
	    ResultSet rs = stmt.executeQuery("SELECT * FROM leisure.area");
	    while(rs.next() != false){
	    	 // Can get columns by name, or number which starts at 1
	    	 String id = rs.getString("area_ID");
	    	 String areaName = rs.getString("area_description");
	    	 String cap = rs.getString("area_capacity");
	    	 full.append(id + " " + areaName + " " + cap + '\n');
	    	}
	    String end = full.toString();
	    return end;
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return "NO VALUES";
	}
	
	
	public static void main(String[] args) {
		
	
		
		leisure_DB l = new leisure_DB();
		
		System.out.println(l.queryAllAreas());
		
	}
}
