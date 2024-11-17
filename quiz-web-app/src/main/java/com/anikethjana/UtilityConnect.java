package com.anikethjana;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UtilityConnect {
	public static Connection getDbConnection()  {
		String url = "jdbc:mysql://localhost:3306/demostudent";
		String user = "root";
		String password = "root123";
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(url,user,password);
		} 
		catch (Exception e) 
		{	System.out.println("Error in getting connection");
			e.getMessage();
			return null;
		}
		
	}
	
	// Closing Connections
	public static void closeConnection(Connection connection) throws Exception  {
		try {
			if(connection!=null)
	        {
	            connection.close();
	        }
		}
        catch(Exception e){
        	e.printStackTrace();
        }
	}
}

