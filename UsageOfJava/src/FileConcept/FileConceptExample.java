package FileConcept;

import java.io.*;
import java.util.*;

class Employee {
    int id;
    String name;
    int age;
    String phone;

    // Constructor
    public Employee(int id, String name, int age, String phone) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    // Method to display employee details
    public void display() {
        System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age + ", Phone: " + phone);
    }
}

public class FileConceptExample {
    public static void main(String[] args) {
        String fileName = "employees.txt"; // Name of the text file
        List<Employee> employees = new ArrayList<>();

        try {
            // Reading data from the file
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] details = line.split(",");
                
                // Parsing employee details
                int id = Integer.parseInt(details[0]);
                String name = details[1];
                int age = Integer.parseInt(details[2]);
                String phone = details[3];

                // Creating an Employee object and adding it to the list
                Employee employee = new Employee(id, name, age, phone);
                employees.add(employee);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            return;
        } catch (Exception e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
            return;
        }

        // Displaying employee details
        System.out.println("Employee Details:");
        for (Employee emp : employees) {
            emp.display();
        }
    }
}
