import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetAllStudents")
public class GetAllStudentsServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/logindb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "aravind";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Allow Cross-Origin Resource Sharing (CORS) if needed
        response.setHeader("Access-Control-Allow-Origin", "*");

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Fetch all users from the 'users' table (as per the updated schema)
            String query = "SELECT * FROM users";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            PrintWriter out = response.getWriter();
            out.print("[");

            boolean first = true;
            while (rs.next()) {
                if (!first) out.print(",");
                first = false;

                out.print("{");

                // Mapping the database columns to JSON response fields
                out.print("\"name\":\"" + escapeJson(rs.getString("name")) + "\",");
                out.print("\"age\":" + rs.getInt("age") + ",");
                out.print("\"gender\":\"" + rs.getString("gender") + "\",");
                out.print("\"phone\":\"" + rs.getString("phone") + "\",");
                out.print("\"email\":\"" + rs.getString("email") + "\",");
                out.print("\"username\":\"" + rs.getString("username") + "\"");

                out.print("}");
            }
            out.print("]");

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(500);
            response.getWriter().write("{\"error\":\"Database connection failed: " + e.getMessage() + "\"}");
        }
    }

    // Utility method to escape special characters in JSON strings
    private String escapeJson(String value) {
        return value == null ? "" : value.replace("\"", "\\\"").replace("\\", "\\\\");
    }
}
