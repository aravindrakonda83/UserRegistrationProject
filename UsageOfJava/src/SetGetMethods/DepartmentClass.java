package SetGetMethods;

//Class: Department
class Department {
 private String deptName;
 private int deptId;
 private int employeeCount;

 // Getter and Setter for deptName
 public String getDeptName() {
     return deptName;
 }

 public void setDeptName(String deptName) {
     this.deptName = deptName;
 }

 // Getter and Setter for deptId
 public int getDeptId() {
     return deptId;
 }

 public void setDeptId(int deptId) {
     this.deptId = deptId;
 }

 // Getter and Setter for employee
 
 public int getEmployeeCount() {
     return employeeCount;
 }

 public void setEmployeeCount(int employeeCount) {
     this.employeeCount = employeeCount;
 }
}

public class DepartmentClass {
 public static void main(String[] args) {
    
     Department department = new Department();
     department.setDeptName("Human Resources");
     department.setDeptId(101);
     department.setEmployeeCount(25);
     System.out.println("Department: " + department.getDeptName() + " (ID: " + department.getDeptId() + ") - Employees: " + department.getEmployeeCount());
 }
}
