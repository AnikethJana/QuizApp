<%@ page import="java.sql.*" %>
<%@ page import="jakarta.servlet.http.Cookie" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<%@ page import="com.anikethjana.UtilityConnect" %>

<%
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        conn = UtilityConnect.getDbConnection();
        String query = "SELECT Name, Scores FROM students ORDER BY Scores DESC";
        stmt = conn.prepareStatement(query);
        rs = stmt.executeQuery();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Leaderboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .leaderboard {
            width: 50%;
            margin: auto;
            border-collapse: collapse;
        }
        .leaderboard th, .leaderboard td {
            border: 1px solid #ddd;
            padding: 8px;
        }
        .leaderboard th {
            background-color: #f2f2f2;
            text-align: left;
        }
    </style>
</head>
<body>
    <h1>Leaderboard</h1>
    <table class="leaderboard">
        <tr>
            <th>Rank</th>
            <th>Name</th>
            <th>Score</th>
        </tr>
        <%
            int rank = 1;
            while (rs.next()) {
                String name = rs.getString("Name");
                int score = rs.getInt("Scores");
        %>
        <tr>
            <td><%= rank %></td>
            <td><%= name %></td>
            <td><%= score %></td>
        </tr>
        <%
                rank++;
            }
        %>
    </table>

    <%
        String email = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies) {
            if (c.getName().equals("Email")) {
                email = c.getValue();
                break;
            }
        }

        if (email != null) {
            String userQuery = "SELECT Name, Scores, FIND_IN_SET(Scores, (SELECT GROUP_CONCAT(Scores ORDER BY Scores DESC) FROM students)) AS Rank FROM students WHERE Email = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(userQuery)) {
                pstmt.setString(1, email);
                ResultSet userRs = pstmt.executeQuery();
                if (userRs.next()) {
                    String userName = userRs.getString("Name");
                    int userRank = userRs.getInt("Rank");
    %>
    <p>You are at <%= userRank %> position, <%= userName %>.</p>
    <%
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        UtilityConnect.closeConnection(conn);
        if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
        if (stmt != null) try { stmt.close(); } catch (SQLException ignore) {}
    }
%>
</body>
</html>
