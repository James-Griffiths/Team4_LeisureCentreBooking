package Leisure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


public class leisure_DB {

	//sql
	final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	final String DB_URL = "jdbc:mysql://localhost:3306/?useSSL=false";
	final String USER_NAME = "root";
	final String PASSWORD = "password";
	Connection conn = null;
	
	
	public leisure_DB(){

	}
	
	//Connection to the database
	private void connectDB(){
		
		try{
			Class.forName(JDBC_DRIVER);
			System.out.println("Driver Registered");
			
						conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
						System.out.println("Connected");
		}
		catch (ClassNotFoundException cnfe){
			System.out.println("Could not load driver.\n" + cnfe.getMessage());
		}
		catch (SQLException e) {
			System.out.println("Problem with SQL.\n" + e.getMessage());
		}
	
	}
	
	//closing of connection to database
	private void disconnectDB(){
		try{
			if(conn != null){
			conn.close();
		    System.out.println("Connection closed.");
			}
		}
		catch (Exception e){
			System.out.println("Could not close connection.\n" + e.getMessage());
		}
	}
	
	
	public String queryAllAreas(){
		connectDB();
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
	    disconnectDB();
	    return end;
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		disconnectDB();
		return "NO VALUES";
	}
	
	public void addArea(String s,int j){
		connectDB();
		try{
			Statement stmt = conn.createStatement();
		    System.out.println("Statement object created"); 
		    
		    stmt.executeUpdate("USE leisure");
		    stmt.executeUpdate("INSERT INTO leisure.area (area_description,area_capacity) VALUES(\'" + s +"\',\'" +j +"\')");
		    
			}
			catch(SQLException e){
				System.out.println(e.getMessage());
			}
		disconnectDB();	
		
	}
	
	public void updateArea(String changeName, String currentName){
		connectDB();
		try{
			Statement stmt = conn.createStatement();
	
			System.out.println("Statement object created"); 
		    
		    
		    
		    stmt.executeUpdate("USE leisure");
		    stmt.executeUpdate("UPDATE leisure.area SET area_description = \'" + changeName + "\' WHERE area_ID = \'" + 11 + "\';" );
		    
			}
			catch(SQLException e){
				System.out.println(e.getMessage());
			}
		disconnectDB();
	}
	
	
	//Remember to thank Hugh J! 
	public String lookAt(int col){
		connectDB();
		try{
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	    System.out.println("Statement object created");
	    
	    StringBuilder full = new StringBuilder();
	    
	    ResultSet rs = stmt.executeQuery("SELECT * FROM leisure.area WHERE area_ID = '" + col + "';");
	    if(rs.next()){
	    	 // Can get columns by name, or number which starts at 1
	    	 String id = rs.getString("area_ID");
	    	 String areaName = rs.getString("area_description");
	    	 String cap = rs.getString("area_capacity");
	    	 full.append(id + " " + areaName + " " + cap + '\n');
	    	}
	    String end = full.toString();
	    disconnectDB();
	    return end;
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		disconnectDB();
		return "NO VALUES";
	}
	
	//Remember that columns in the database are zero delimited (start at 0)
	//Also string date must be in the form of yyyy-mm-dd otherwise CRASHHHHHHHHHH
	public void updateBooking(String inDate, int classid, int areaid,int custid){
		connectDB();
		java.sql.PreparedStatement pState = null;
		java.sql.Date sqlDate = java.sql.Date.valueOf(inDate);
		//String sql = "INSERT INTO team4_lesiurecentre.booking (booking_date, class_ID,area_ID, customer_ID) VALUES (booking_date = ?,class_ID = ?,area_ID=?,class_ID = ?);";
	
		String sql = "INSERT INTO leisure.booking (booking_date, class_ID,area_ID, customer_ID) VALUES (?, ?, ?, ?);";
	

		//team4_lesiurecentre.booking
		 try {
				
			 
			
		        pState = conn.prepareStatement(sql);
		       
		        pState.setDate(1, sqlDate);
		        pState.setInt(2, classid);
		        pState.setInt(3, areaid);
		        pState.setInt(4, custid);
		  
		        pState.execute();
		           
		 }
		catch(SQLException se){
			System.out.println(se.getMessage());
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			disconnectDB();
		}
	}
	
	public static void main(String[] args) {
		
	
		
		leisure_DB l = new leisure_DB();
		
		System.out.println(l.queryAllAreas());
		
		
		
		// date format is unchangeable unfortunately! so
		// time could/should be linked in the class rather than booking
		l.updateBooking("2017-03-28",1,1,1);
		
		
		//l.addArea("Blues",20);
		
		//System.out.println(l.queryAllAreas());
		
		
		//System.out.println(l.lookAt(2));
		
		
		//l.updateArea("HELELELELE", "Blues");
		
		//System.out.println(l.queryAllAreas());
		
	}
}
