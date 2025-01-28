package MarshallingDemo;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;

public class MarshallingFileExample {
    public static void main(String[] args) {
        try {
            
            File file = new File("src\\main\\resources\\student.xml");

            
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                boolean dirsCreated = parentDir.mkdirs();
                if (dirsCreated) {
                    System.out.println("Created directories: " + parentDir.getAbsolutePath());
                }
            }

            
            User user = new User();
            user.setId(1);
            user.setName("John Doe");
            user.setEmail("john.doe@example.com");

            
            XmlMapper xmlMapper = new XmlMapper();

            
            xmlMapper.writeValue(file, user);
            System.out.println("XML written to file: " + file.getAbsolutePath());

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

