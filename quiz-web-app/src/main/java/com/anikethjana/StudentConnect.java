package com.anikethjana;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentConnect {

	public static boolean loginUser(String Email, String Password) {
		String query = "SELECT * FROM Students WHERE Email = ? AND Password = ?";
		
		try (Connection connection = UtilityConnect.getDbConnection();
			PreparedStatement pstmt = connection.prepareStatement(query)
			)
		{
			pstmt.setString(1, Email);
			pstmt.setString(2, Password);
			
			ResultSet rs = pstmt.executeQuery();
			return rs.next();
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean registerUser(String Name, String Password, String Email, int Age) {
	    String queryCheck = "SELECT * FROM Students WHERE Email = ?";
	    String queryInsert = "INSERT INTO Students (Name, Password, Email, Age) VALUES (?, ?, ?, ?)";
	    
	    try (Connection connection = UtilityConnect.getDbConnection();
	         PreparedStatement checkStmt = connection.prepareStatement(queryCheck)) {
	    	
	        checkStmt.setString(1, Email);
	        try (ResultSet rs = checkStmt.executeQuery()) {
	            if (rs.next()) {
	                System.out.println("User Exists");
	                return false;
	            }
	        }
	        
	        // If user does not exist, insert the new user
	        try (PreparedStatement insertStmt = connection.prepareStatement(queryInsert)) {
	            insertStmt.setString(1, Name);
	            insertStmt.setString(2, Password);
	            insertStmt.setString(3, Email);
	            insertStmt.setInt(4, Age);
	            
	            int rowsAffected = insertStmt.executeUpdate();
	            return rowsAffected > 0;
	        }
	        
	    } catch (Exception e) {
	        System.out.println("Error: \n");
	        e.printStackTrace();
	        return false;
	    }
	}

	
	public static boolean updateScore(String email, int score) {
	    String query = "UPDATE Students SET Scores = Scores + ? WHERE Email = ?";
	    try (Connection connection = UtilityConnect.getDbConnection();
	         PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setInt(1, score);
	        pstmt.setString(2, email);

	        int rowsAffected = pstmt.executeUpdate();
	        return rowsAffected > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

}
