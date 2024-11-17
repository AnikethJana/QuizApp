package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import com.anikethjana.StudentConnect;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
@MultipartConfig
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email").strip();
        String password = request.getParameter("password").strip();

        response.setContentType("text/plain"); // Use plain text for AJAX response

        try (PrintWriter out = response.getWriter()) {
            if (!StudentConnect.loginUser(email, password)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Set appropriate HTTP status
                out.println("Login Unsuccessful");
            } else {
                // Create secure cookie
                Cookie cookie = new Cookie("Email", email);
                cookie.setHttpOnly(true); // Prevent client-side access
                cookie.setSecure(request.isSecure());  // Send cookie only over HTTPS
                cookie.setPath("/");     // Apply to the entire application
                response.addCookie(cookie);

                // Log redirection attempt
                log("Login successful, redirecting to askquiz.html");

                // Redirect to askquiz.html
                response.setContentType("application/json");
                out.println("{\"success\":true}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try (PrintWriter out = response.getWriter()) {
                out.println("An error occurred while processing your request.");
            }
            e.printStackTrace(); // Log the exception (optional)
        }
    }
}
