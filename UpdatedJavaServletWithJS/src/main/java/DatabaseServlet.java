import java.io.BufferedReader;
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
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Updatedjs_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "aravind";
    private static final String TABLE_NAME = "Users";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Get the ID parameter from the request
            String idParam = request.getParameter("id");

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

            // Build JSON response
            StringBuilder json = new StringBuilder("[");
            boolean hasResults = false;

            while (rs.next()) {
                hasResults = true;
                if (json.length() > 1) {
                    json.append(",");
                }
                json.append("{");
                json.append("\"id\":").append(rs.getInt("id")).append(",");
                json.append("\"name\":\"").append(rs.getString("name")).append("\",");
                json.append("\"email\":\"").append(rs.getString("email")).append("\",");
                json.append("\"age\":").append(rs.getInt("age")).append(",");
                json.append("\"phone\":\"").append(rs.getString("phone")).append("\",");
                json.append("\"address\":\"").append(rs.getString("address")).append("\",");
                json.append("\"city\":\"").append(rs.getString("city")).append("\",");
                json.append("\"state\":\"").append(rs.getString("state")).append("\",");
                json.append("\"zip\":\"").append(rs.getString("zip")).append("\",");
                json.append("\"country\":\"").append(rs.getString("country")).append("\",");
                json.append("\"gender\":\"").append(rs.getString("gender")).append("\"");
                json.append("}");
            }

            json.append("]");
            if (!hasResults) {
                json = new StringBuilder("[]");
            }

            out.print(json.toString());
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace(out);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"Database driver not found\"}");
        } catch (SQLException e) {
            e.printStackTrace(out);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"Error fetching data: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
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

            out.print("{\"message\":\"User added successfully!\"}");
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace(out);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"Database driver not found\"}");
        } catch (SQLException e) {
            e.printStackTrace(out);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"Error inserting data: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        // Read JSON data from the request body
        StringBuilder jsonInput = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonInput.append(line);
            }
        }

        try {
            // Parse the JSON data
            org.json.JSONObject jsonObject = new org.json.JSONObject(jsonInput.toString());
            int id = jsonObject.getInt("id");
            String name = jsonObject.getString("name");
            String email = jsonObject.getString("email");
            int age = jsonObject.getInt("age");
            String phone = jsonObject.getString("phone");
            String address = jsonObject.getString("address");
            String city = jsonObject.getString("city");
            String state = jsonObject.getString("state");
            String zip = jsonObject.getString("zip");
            String country = jsonObject.getString("country");
            String gender = jsonObject.getString("gender");

            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Update user data in the database
            String query = "UPDATE " + TABLE_NAME +
                    " SET name = ?, email = ?, age = ?, phone = ?, address = ?, city = ?, state = ?, zip = ?, country = ?, gender = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setInt(3, age);
            ps.setString(4, phone);
            ps.setString(5, address);
            ps.setString(6, city);
            ps.setString(7, state);
            ps.setString(8, zip);
            ps.setString(9, country);
            ps.setString(10, gender);
            ps.setInt(11, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                out.print("{\"message\":\"User updated successfully!\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\":\"User not found\"}");
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace(out);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"Error updating data: " + e.getMessage() + "\"}");
        }
    }
}
