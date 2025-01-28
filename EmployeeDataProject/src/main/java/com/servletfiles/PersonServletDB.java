package com.servletfiles;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PersonServletDB")
public class PersonServletDB extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String bloodGroup = request.getParameter("bloodGroup");
        String number = request.getParameter("number");
        String email = request.getParameter("email");

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        try {
            // Load JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to Database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaIsoft_DB", "root", "aravind");

            // Prepare Query
            String query = "INSERT INTO PersonData (name, age, bloodGroup, number, email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, bloodGroup);
            ps.setString(4, number);
            ps.setString(5, email);

            // Execute Update
            int result = ps.executeUpdate();

            if (result > 0) {
                out.println("<h3>Person added successfully!</h3>");
            } else {
                out.println("<h3>Error adding person. Please try again.</h3>");
            }

            ps.close();
            con.close();
        } catch (ClassNotFoundException e) {
            out.println("<h3>JDBC Driver not found!</h3>");
            e.printStackTrace(out);
        } catch (Exception e) {
            out.println("<h3>Database Error!</h3>");
            e.printStackTrace(out);
        }
    }
}