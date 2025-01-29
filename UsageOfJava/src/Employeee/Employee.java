package Employeee;

import javax.swing.*;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class Employee {
	// HashMap to store employee details
	private static HashMap<String, Employee> employeeDatabase = new HashMap<>();
	public Employee(String employeeID, String trim, String trim2, String trim3, String trim4, String trim5) {
	}

	public static void main(String[] args) {
		// Main frame
		JFrame frame = new JFrame("Employee Form");
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(8, 2, 10, 10));

		// Components for the form
		JLabel lblEmployeeID = new JLabel("Employee ID:");
		JTextField txtEmployeeID = new JTextField();

		JLabel lblEmployeeName = new JLabel("Employee Name:");
		JTextField txtEmployeeName = new JTextField();

		JLabel lblDateOfBirth = new JLabel("Date of Birth:");
		JTextField txtDateOfBirth = new JTextField();
		
		JLabel lblDesignation = new JLabel("Designation");
		JTextField txtDesignation = new JTextField();

		JLabel lblCity = new JLabel("City:");
		JTextField txtCity = new JTextField();

		JLabel lblEmailID = new JLabel("Email ID:");
		JTextField txtEmailID = new JTextField();

		JLabel lblMobileNumber = new JLabel("Mobile Number:");
		JTextField txtMobileNumber = new JTextField();

		JButton btnSubmit = new JButton("Submit");
		JButton btnReset = new JButton("Reset");
		JButton btnGetByID = new JButton("Get By ID");

		// Adding components to the frame
		frame.add(lblEmployeeID);
		frame.add(txtEmployeeID);
		frame.add(lblEmployeeName);
		frame.add(txtEmployeeName);
		frame.add(lblDateOfBirth);
		frame.add(txtDateOfBirth);
		frame.add(lblDesignation);
		frame.add(txtDesignation);
		frame.add(lblCity);
		frame.add(txtCity);
		frame.add(lblEmailID);
		frame.add(txtEmailID);
		frame.add(lblMobileNumber);
		frame.add(txtMobileNumber);
		frame.add(btnSubmit);
		frame.add(btnReset);
		frame.add(new JLabel()); // Empty space
		frame.add(btnGetByID);

		// Button functionalities
		btnSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String employeeID = txtEmployeeID.getText().trim();
				if (employeeID.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Employee ID cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				Employee employee = new Employee(
						employeeID,
						txtEmployeeName.getText().trim(),
						txtDateOfBirth.getText().trim(),
						txtCity.getText().trim(),
						txtEmailID.getText().trim(),
						txtMobileNumber.getText().trim()
						);
				employeeDatabase.put(employeeID, employee);
				JOptionPane.showMessageDialog(frame, "Employee details saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtEmployeeID.setText("");
				txtEmployeeName.setText("");
				txtDateOfBirth.setText("");
				txtCity.setText("");
				txtEmailID.setText("");
				txtMobileNumber.setText("");
			}
		});

		btnGetByID.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Open a new frame for GetByID functionality
				JFrame getByIDFrame = new JFrame("Get Employee By ID");
				getByIDFrame.setSize(300, 150);
				getByIDFrame.setLayout(new GridLayout(3, 1, 10, 10));

				JLabel lblID = new JLabel("Enter Employee ID:");
				JTextField txtID = new JTextField();
				JButton btnSearch = new JButton("Search");

				getByIDFrame.add(lblID);
				getByIDFrame.add(txtID);
				getByIDFrame.add(btnSearch);

				btnSearch.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String searchID = txtID.getText().trim();
						if (employeeDatabase.containsKey(searchID)) {
							Employee employee = employeeDatabase.get(searchID);
							JOptionPane.showMessageDialog(getByIDFrame, employee.toString(), "Employee Details", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(getByIDFrame, "No details found for Employee ID: " + searchID, "Not Found", JOptionPane.ERROR_MESSAGE);
						}
					}
				});

				getByIDFrame.setVisible(true);
			}
		});

		frame.setVisible(true);
	}

}
