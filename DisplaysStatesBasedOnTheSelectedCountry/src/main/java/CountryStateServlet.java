import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CountryStateServlet")
public class CountryStateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String country = request.getParameter("country");
        String dbUrl = "jdbc:mysql://localhost:3306/country_state_db";
        String dbUser = "root";
        String dbPassword = "aravind"; 

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            
            String query = "SELECT state_name FROM countries_states WHERE country_name = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, country);

            ResultSet rs = stmt.executeQuery();
            
            StringBuilder json = new StringBuilder("[");
            while (rs.next()) {
                if (json.length() > 1) {
                    json.append(",");
                }
                json.append("{\"state\":\"").append(rs.getString("state_name")).append("\"}");
            }
            json.append("]");
            out.print(json.toString());

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.print("[]");
        }
    }
}
