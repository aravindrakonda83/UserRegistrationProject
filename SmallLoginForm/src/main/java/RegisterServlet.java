import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/logindb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "aravind";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the form data
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String gender = request.getParameter("gender");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Establish a connection to the database
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // SQL query to insert new user data
            String query = "INSERT INTO users (name, age, gender, phone, email, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, gender);
            stmt.setString(4, phone);
            stmt.setString(5, email);
            stmt.setString(6, username);
            stmt.setString(7, password);

            // Execute the update to insert the data
            stmt.executeUpdate();

            // Send success response
            HttpSession session = request.getSession();
            session.setAttribute("name", name);
            session.setAttribute("age", age);
            session.setAttribute("gender", gender);
            session.setAttribute("phone", phone);
            session.setAttribute("email", email);
            session.setAttribute("username", username);

            // Debug output
            System.out.println("Session Data - Name: " + name);
            System.out.println("Session Data - Age: " + age);
            System.out.println("Session Data - Gender: " + gender);
            System.out.println("Session Data - Phone: " + phone);
            System.out.println("Session Data - Email: " + email);
            System.out.println("Session Data - Username: " + username);
            
            // Redirect to dashboard.jsp
            response.sendRedirect("dashboard.jsp");
    
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }
}
