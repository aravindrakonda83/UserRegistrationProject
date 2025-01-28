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

@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database credentials
    String DB_URL = "jdbc:mysql://localhost:3306/employee_db";
    String DB_USER = "root";
    String DB_PASSWORD = "aravind"; // Replace with your password

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve input values from the form
        String employeeName = request.getParameter("employeeName");
        String employeeId = request.getParameter("employeeId");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String department = request.getParameter("department");

        response.setContentType("text/html");

        // Insert employee data into the database
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO employees (id, name, email, phone, department) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setString(1, employeeId);
            stmt.setString(2, employeeName);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, department);

            int rowsInserted = stmt.executeUpdate();

            PrintWriter out = response.getWriter();
            if (rowsInserted > 0) {
                out.println("<html><head>");
                out.println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css'>");
                out.println("</head><body><div class='container mt-5'>");
                out.println("<h1 class='text-center'>Employee Added Successfully!</h1>");
                out.println("<p>Employee Name: " + employeeName + "</p>");
                out.println("<p>Email: " + email + "</p>");
                out.println("<p>Phone: " + phone + "</p>");
                out.println("<p>Department: " + department + "</p>");
                out.println("<a href='index.html' class='btn btn-primary'>Add Another Employee</a>");
                out.println("</div></body></html>");
            }

        } catch (SQLException e) {
            response.getWriter().println("<h3>Error occurred: " + e.getMessage() + "</h3>");
        }
    }
}
