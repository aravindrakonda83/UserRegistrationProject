import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/GetStatesServlet")
public class GetStatesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/states_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "aravind";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String country = request.getParameter("country");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // List to store states
        List<String> states = new ArrayList<>();

        // SQL Query
        String query = "SELECT state FROM states WHERE country = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, country);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                states.add(rs.getString("state"));
            }

            // Create a JSON response manually
            StringBuilder jsonResponse = new StringBuilder("[");
            for (int i = 0; i < states.size(); i++) {
                jsonResponse.append("\"").append(states.get(i)).append("\"");
                if (i < states.size() - 1) {
                    jsonResponse.append(", ");
                }
            }
            jsonResponse.append("]");

            // Send the JSON response
            response.getWriter().write(jsonResponse.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching states");
        }
    }
}
