

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

@WebServlet("/GetStudentById")
public class GetStudentByIdServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/logindb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "aravind";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.setHeader("Access-Control-Allow-Origin", "*");

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.setStatus(400);
            response.getWriter().write("{\"error\":\"ID parameter is required\"}");
            return;
        }

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM users WHERE id = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, Integer.parseInt(idParam));
            ResultSet rs = pst.executeQuery();

            PrintWriter out = response.getWriter();

            if (rs.next()) {
                out.print("{");
                out.print("\"name\":\"" + escapeJson(rs.getString("name")) + "\",");
                out.print("\"age\":" + rs.getInt("age") + ",");
                out.print("\"gender\":\"" + rs.getString("gender") + "\",");
                out.print("\"phone\":\"" + rs.getString("phone") + "\",");
                out.print("\"email\":\"" + rs.getString("email") + "\",");
                out.print("\"username\":\"" + rs.getString("username") + "\"");
                out.print("}");
            } else {
                response.setStatus(404);
                response.getWriter().write("{\"error\":\"User not found\"}");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(500);
            response.getWriter().write("{\"error\":\"Database connection failed: " + e.getMessage() + "\"}");
        }
    }

    private String escapeJson(String value) {
        return value == null ? "" : value.replace("\"", "\\\"").replace("\\", "\\\\");
    }
}
