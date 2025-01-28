package MarshallingDemo;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;

public class UnmarshallingFileExample {
    public static void main(String[] args) {
        try {
            
            File file = new File("src\\main\\resources\\student.xml");

            XmlMapper xmlMapper = new XmlMapper();

            User unmarshalledUser = xmlMapper.readValue(file, User.class);
            System.out.println("\nUnmarshalled User Object:");
            System.out.println(unmarshalledUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

