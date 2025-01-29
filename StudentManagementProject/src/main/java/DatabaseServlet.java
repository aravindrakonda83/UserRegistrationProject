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


@WebServlet("/DatabaseServlet")
public class DatabaseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/js_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "aravind";

   
    private static final String TABLE_NAME = "Users";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Get the ID parameter from the request
            String idParam = request.getParameter("id");
            System.out.println("Received ID: " + idParam);  // Debugging line

            String query;
            PreparedStatement ps;

            if (idParam != null && !idParam.isEmpty()) {
                // If ID is provided, fetch the user data by ID
                query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
                ps = conn.prepareStatement(query);
                ps.setInt(1, Integer.parseInt(idParam));
            } else {
                // If no ID is provided, fetch all users
                query = "SELECT * FROM " + TABLE_NAME;
                ps = conn.prepareStatement(query);
            }

            ResultSet rs = ps.executeQuery();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Database Data</title>");
            out.println("<style>");
            out.println("table { width: 100%; border-collapse: collapse; }");
            out.println("th, td { border: 1px solid black; padding: 8px; text-align: left; }");
            out.println("th { background-color: #f2f2f2; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            out.println("<h1>Database Data</h1>");
            out.println("<table>");
            out.println("<thead><tr><th>ID</th><th>Name</th><th>Email</th><th>Age</th><th>Phone</th>" +
                    "<th>Address</th><th>City</th><th>State</th><th>Zip</th><th>Country</th><th>Gender</th></tr></thead>");
            out.println("<tbody>");

            boolean hasResults = false;
            while (rs.next()) {
                hasResults = true;
                out.println("<tr>");
                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("email") + "</td>");
                out.println("<td>" + rs.getInt("age") + "</td>");
                out.println("<td>" + rs.getString("phone") + "</td>");
                out.println("<td>" + rs.getString("address") + "</td>");
                out.println("<td>" + rs.getString("city") + "</td>");
                out.println("<td>" + rs.getString("state") + "</td>");
                out.println("<td>" + rs.getString("zip") + "</td>");
                out.println("<td>" + rs.getString("country") + "</td>");
                out.println("<td>" + rs.getString("gender") + "</td>");
                out.println("</tr>");
            }

            if (!hasResults) {
                out.println("<tr><td colspan='11'>No data found for the given ID.</td></tr>");
            }

            out.println("</tbody></table>");
            out.println("</body>");
            out.println("</html>");

            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace(out);
            out.println("Database driver not found: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace(out);
            out.println("Error fetching data: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        // Get form parameters
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String age = request.getParameter("age");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zip = request.getParameter("zip");
        String country = request.getParameter("country");
        String gender = request.getParameter("gender");

        // Validate inputs
        if (name == null || name.isEmpty() || !name.matches("[A-Za-z\\s]+") || name.length() > 50) {
            out.println("Invalid name.");
            return;
        }

        if (email == null || email.isEmpty() || !email.matches("^\\S+@\\S+\\.\\S+$")) {
            out.println("Invalid email.");
            return;
        }

        if (age == null || !age.matches("\\d+") || Integer.parseInt(age) <= 0 || Integer.parseInt(age) > 120) {
            out.println("Invalid age.");
            return;
        }

        if (phone == null || !phone.matches("\\d{10}")) {
            out.println("Invalid phone number.");
            return;
        }

        if (zip == null || !zip.matches("\\d{6}")) {
            out.println("Invalid ZIP code.");
            return;
        }

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Insert data into the table
            String query = "INSERT INTO " + TABLE_NAME +
                    " (name, email, age, phone, address, city, state, zip, country, gender) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setInt(3, Integer.parseInt(age));
            ps.setString(4, phone);
            ps.setString(5, address);
            ps.setString(6, city);
            ps.setString(7, state);
            ps.setString(8, zip);
            ps.setString(9, country);
            ps.setString(10, gender);

            ps.executeUpdate();

            out.println("User added successfully!");
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace(out);
            out.println("Database driver not found: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace(out);
            out.println("Error inserting data: " + e.getMessage());
        }
    }
}