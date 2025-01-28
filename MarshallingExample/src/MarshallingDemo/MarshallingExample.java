package MarshallingDemo;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class MarshallingExample {
    public static void main(String[] args) {
        try {
            
            User user = new User();
            user.setId(1);
            user.setName("John Doe");
            user.setEmail("john.doe@example.com");

            
            XmlMapper xmlMapper = new XmlMapper();

            
            String xmlString = xmlMapper.writeValueAsString(user);
            
            System.out.println("Marshalled XML:");
            System.out.println(xmlString);

            
            User unmarshalledUser = xmlMapper.readValue(xmlString, User.class);
            System.out.println("\nUnmarshalled User Object:");
            System.out.println(unmarshalledUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
