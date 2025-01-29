import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String hashedPassword = null;
		try {
			hashedPassword = hashPassword(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Hash the entered password

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            // Connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdb", "username", "password");

            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, hashedPassword);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                // Login success, display user details
                response.setContentType("application/json");
                response.getWriter().write("{"
                        + "\"name\": \"" + rs.getString("name") + "\","
                        + "\"email\": \"" + rs.getString("email") + "\","
                        + "\"age\": " + rs.getInt("age") + ","
                        + "\"phone\": \"" + rs.getString("phone") + "\","
                        + "\"address\": \"" + rs.getString("address") + "\","
                        + "\"city\": \"" + rs.getString("city") + "\","
                        + "\"state\": \"" + rs.getString("state") + "\","
                        + "\"zip\": \"" + rs.getString("zip") + "\","
                        + "\"country\": \"" + rs.getString("country") + "\","
                        + "\"gender\": \"" + rs.getString("gender") + "\""
                        + "}");
            } else {
                // Invalid credentials
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"message\": \"Invalid login credentials\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("An error occurred.");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
