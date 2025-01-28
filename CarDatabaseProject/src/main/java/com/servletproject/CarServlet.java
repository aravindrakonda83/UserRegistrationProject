package com.servletproject;

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

@WebServlet("/CarServlet")
public class CarServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database credentials
    String DB_URL = "jdbc:mysql://localhost:3306/car_db";
    String DB_USER = "root";
    String DB_PASSWORD = "aravind"; // Replace with your MySQL password

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve input values from the form
        String carId = request.getParameter("carId");
        String carBrand = request.getParameter("carBrand");
        String carModel = request.getParameter("carModel");
        String carYear = request.getParameter("carYear");
        String carPrice = request.getParameter("carPrice");

        response.setContentType("text/html");

        // Insert car data into the database
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO cars (id, brand, model, year, price) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setString(1, carId);
            stmt.setString(2, carBrand);
            stmt.setString(3, carModel);
            stmt.setString(4, carYear);
            stmt.setString(5, carPrice);

            int rowsInserted = stmt.executeUpdate();

            PrintWriter out = response.getWriter();
            if (rowsInserted > 0) {
                out.println("<html><head>");
                out.println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css'>");
                out.println("</head><body><div class='container mt-5'>");
                out.println("<h1 class='text-center'>Car Added Successfully!</h1>");
                out.println("<p>Car Brand: " + carBrand + "</p>");
                out.println("<p>Car Model: " + carModel + "</p>");
                out.println("<p>Year: " + carYear + "</p>");
                out.println("<p>Price: $" + carPrice + "</p>");
                out.println("<a href='index.html' class='btn btn-primary'>Add Another Car</a>");
                out.println("</div></body></html>");
            }

        } catch (SQLException e) {
            response.getWriter().println("<h3>Error occurred: " + e.getMessage() + "</h3>");
        }
    }
}
