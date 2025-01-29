import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CountryStateServlet")
public class CountryStateServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String countryParam = request.getParameter("country");
        String countriesOnly = request.getParameter("countries");

        String dbUrl = "jdbc:mysql://localhost:3306/userreg_db";
        String dbUser = "root";
        String dbPassword = "aravind";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String query;
            if ("true".equals(countriesOnly)) {
                query = "SELECT DISTINCT country_name FROM countries_states";
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();

                out.print("[");
                while (rs.next()) {
                    if (!rs.isFirst()) out.print(",");
                    out.print("{\"name\":\"" + rs.getString("country_name") + "\"}");
                }
                out.print("]");
            } else if (countryParam != null) {
                query = "SELECT state_name FROM countries_states WHERE country_name = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, countryParam);
                ResultSet rs = stmt.executeQuery();

                out.print("[");
                while (rs.next()) {
                    if (!rs.isFirst()) out.print(",");
                    out.print("{\"name\":\"" + rs.getString("state_name") + "\"}");
                }
                out.print("]");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.print("[]");
        }
    }
}
