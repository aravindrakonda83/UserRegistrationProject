package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/ServletData")
public class ServletData extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Read JSON data from the request
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String jsonData = sb.toString();

        
        System.out.println("Received JSON: " + jsonData);

        // Parse the JSON data
        JSONObject receivedData = new JSONObject(jsonData);

        // Create a response object
        JSONArray responseData = new JSONArray();
        responseData.put(receivedData); 

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("status", "success");
        jsonResponse.put("message", "Data received successfully");
        jsonResponse.put("data", responseData);

        
        System.out.println("Response JSON: " + jsonResponse.toString(4)); // Pretty print

        
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse.toString());
    }
}
