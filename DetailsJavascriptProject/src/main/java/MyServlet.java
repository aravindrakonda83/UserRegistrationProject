import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/details_db"; // Update with your DB name
    private static final String DB_USERNAME = "root";  // Update with your DB username
    private static final String DB_PASSWORD = "aravind";  // Update with your DB password

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            sb.append(line);
        }

        String jsonData = sb.toString();
        System.out.println("Received JSON: " + jsonData);

        String name = extractJsonValue(jsonData, "name");
        String email = extractJsonValue(jsonData, "email");
        System.out.println("Parsed Data - Name: " + name + ", Email: " + email);

        if (name != null && email != null) {
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                System.out.println("Database connection successful.");

                String query = "INSERT INTO users (name, email) VALUES (?, ?)";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, name);
                ps.setString(2, email);

                int rowsAffected = ps.executeUpdate();
                PrintWriter out = response.getWriter();

                if (rowsAffected > 0) {
                    System.out.println("Data successfully inserted.");
                    out.println("Data successfully stored in the database!");
                } else {
                    System.out.println("No rows affected.");
                    out.println("Error: Data was not inserted.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("SQL Error: " + e.getMessage());
            }
        } else {
            response.getWriter().println("Error: Invalid input.");
        }
    }

    private String extractJsonValue(String json, String key) {
        String value = null;
        try {
            int startIndex = json.indexOf("\"" + key + "\"") + key.length() + 3;
            int endIndex = json.indexOf("\"", startIndex);
            if (startIndex > -1 && endIndex > -1) {
                value = json.substring(startIndex, endIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
