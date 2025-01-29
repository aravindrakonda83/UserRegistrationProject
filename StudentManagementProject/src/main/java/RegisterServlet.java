import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");  // Get password
        String hashedPassword = null;
		try {
			hashedPassword = hashPassword(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  // Hash the password
        int age = Integer.parseInt(request.getParameter("age"));
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zip = request.getParameter("zip");
        String country = request.getParameter("country");
        String gender = request.getParameter("gender");

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // Connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdb", "username", "password");

            String sql = "INSERT INTO users (name, email, password, age, phone, address, city, state, zip, country, gender) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, hashedPassword);
            pstmt.setInt(4, age);
            pstmt.setString(5, phone);
            pstmt.setString(6, address);
            pstmt.setString(7, city);
            pstmt.setString(8, state);
            pstmt.setString(9, zip);
            pstmt.setString(10, country);
            pstmt.setString(11, gender);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                response.getWriter().write("User registered successfully.");
            } else {
                response.getWriter().write("Error registering user.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("An error occurred.");
        } finally {
            try {
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
