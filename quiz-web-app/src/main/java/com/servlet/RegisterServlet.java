package com.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import com.anikethjana.*;

@WebServlet("/RegisterServlet")
@MultipartConfig
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            int age = Integer.parseInt(request.getParameter("age"));

            // Register user and handle result
            boolean isRegistered = StudentConnect.registerUser(name, password, email, age);
            if (isRegistered) {
                Cookie cookie = new Cookie("Email", email);
                response.addCookie(cookie);

                out.println("Registration Successful. Redirecting...");
                response.setHeader("Refresh", "2; URL=login.html");
            } else {
                out.println("Error: Registration failed. Try again.");
            }
        } catch (Exception e) {
            out.println("Error: An unexpected error occurred.");
            e.printStackTrace();
        }
    }
}
