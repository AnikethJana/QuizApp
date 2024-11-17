package com.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import com.anikethjana.StudentConnect;

@WebServlet("/UpdateScore")
public class UpdateScore extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Correct the parameter name to match the one in the JavaScript
        int score = Integer.parseInt(request.getParameter("score"));
        String email = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("Email")) {
                    email = c.getValue();
                }
            }
        }

        boolean updateSuccessful = StudentConnect.updateScore(email, score);

        if (updateSuccessful) {
            response.sendRedirect("leaderboard.jsp"); // Redirect to leaderboard.jsp after updating the score
        } else {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("Score Update Error");
        }
    }
}
