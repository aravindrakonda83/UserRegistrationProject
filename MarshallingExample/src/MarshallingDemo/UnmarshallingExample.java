package MarshallingDemo;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UnmarshallingExample {
    public static void main(String[] args) {
        try {
            // Create ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // JSON string
            String jsonString = "{\"id\":1,\"name\":\"John Doe\",\"email\":\"john.doe@example.com\"}";

            // Convert JSON string to User object
            User user = objectMapper.readValue(jsonString, User.class);

            // Print the User object
            System.out.println("User Object: " + user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
