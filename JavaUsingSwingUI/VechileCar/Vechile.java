package VechileCar;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import javax.swing.*;

class Car {
    String name;
    String model;
    String price;
    String petrolium;
    String milage;

    public Car(String name, String model, String price, String petrolium, String milage) {
        this.name = name;
        this.model = model;
        this.price = price;
        this.petrolium = petrolium;
        this.milage = milage;
    }

    public String toString() {
        return "Name: " + name + ", Model: " + model + ", Price: " + price + ", Petrolium: " + petrolium + ", Milage: " + milage;
    }
}

public class Vechile {
    public static void main(String[] args) {
        HashMap<String, Car> carData = new HashMap<>();

        JFrame frame = new JFrame("Vehicle Car");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel layout = new JPanel();
        layout.setLayout(new GridLayout(10, 2, 10, 10));

        JLabel nameLabel = new JLabel("Name: ");
        JTextField nameField = new JTextField();

        JLabel modelLabel = new JLabel("Model: ");
        JTextField modelField = new JTextField();

        JLabel priceLabel = new JLabel("Price: ");
        JTextField priceField = new JTextField();

        JLabel petroliumLabel = new JLabel("Petrolium: ");
        JTextField petroliumField = new JTextField();

        JLabel milageLabel = new JLabel("Milage: ");
        JTextField milageField = new JTextField();

        JButton submitButton = new JButton("Submit");
        JButton getNameButton = new JButton("Get by Name");
        JButton readFileButton = new JButton("Read File");
        JButton resetButton = new JButton("Reset");

        layout.add(nameLabel);
        layout.add(nameField);
        layout.add(modelLabel);
        layout.add(modelField);
        layout.add(priceLabel);
        layout.add(priceField);
        layout.add(petroliumLabel);
        layout.add(petroliumField);
        layout.add(milageLabel);
        layout.add(milageField);
        layout.add(submitButton);
        layout.add(getNameButton);
        layout.add(readFileButton);
        layout.add(resetButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String carName = nameField.getText().trim();
                if (carName.isEmpty() || modelField.getText().trim().isEmpty() || priceField.getText().trim().isEmpty()
                        || petroliumField.getText().trim().isEmpty() || milageField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Car car = new Car(carName, modelField.getText().trim(), priceField.getText().trim(),
                        petroliumField.getText().trim(), milageField.getText().trim());
                carData.put(carName, car);

                try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\arvin\\OneDrive\\Desktop\\JavaDev\\car_data.txt", true))) {
                    writer.write(car.toString());
                    writer.newLine();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error writing to file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(frame, "Car added successfully:" + car.toString(), "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        getNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String carName = JOptionPane.showInputDialog(frame, "Enter the car name:");
                if (carName == null || carName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Car car = carData.get(carName.trim());
                if (car != null) {
                    JOptionPane.showMessageDialog(frame, "Car details:\n" + car.toString(), "Car Found",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Car not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        readFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\arvin\\OneDrive\\Desktop\\JavaDev\\car_data.txt"))) {
                    StringBuilder content = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                    JOptionPane.showMessageDialog(frame, "File Content:\n" + content.toString(), "File Read",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error reading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
                modelField.setText("");
                priceField.setText("");
                petroliumField.setText("");
                milageField.setText("");
                JOptionPane.showMessageDialog(frame, "Form reset successfully!", "Reset", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        frame.add(layout);
        frame.setVisible(true);
    }
}
