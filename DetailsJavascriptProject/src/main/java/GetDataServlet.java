import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetData")
public class GetDataServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/details_db"; // Update with your DB name
    private static final String DB_USERNAME = "root";  // Update with your DB username
    private static final String DB_PASSWORD = "aravind";  // Update with your DB password

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");  // Make sure the response is in JSON format
        JSONObject jsonResponse = new JSONObject();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT id, name, email FROM users";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            JSONArray users = new JSONArray();

            // Loop through the result set and create JSON objects for each user
            while (rs.next()) {
                JSONObject user = new JSONObject();
                user.put("id", rs.getInt("id"));
                user.put("name", rs.getString("name"));
                user.put("email", rs.getString("email"));
                users.put(user);
            }

            jsonResponse.put("users", users);  // Wrap the array of users in the JSON response
            response.getWriter().println(jsonResponse.toString());  // Send the JSON response back to the client

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "SQL Error: " + e.getMessage());
            response.getWriter().println(jsonResponse.toString());
        }
    }
}
