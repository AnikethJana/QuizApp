package com.anikethjana;

public class testcase {
	    public static void main(String[] args) {
	        testRegisterUser();
	        testLoginUser();
	        testUpdateScore();
	    }

	    public static void testRegisterUser() {
	        boolean result = StudentConnect.registerUser("Jaane", "password123", "jaane@example.com", 25);
	        System.out.println("Register User Test: " + (result ? "PASSED" : "FAILED"));
	    }

	    public static void testLoginUser() {
	        boolean result = StudentConnect.loginUser("johndoe@example.com", "password123");
	        System.out.println("Login User Test: " + (result ? "PASSED" : "FAILED"));
	    }

	    public static void testUpdateScore() {
	        boolean result = StudentConnect.updateScore("johndoe@example.com", 10);
	        System.out.println("Update Score Test: " + (result ? "PASSED" : "FAILED"));
	    }
	}


