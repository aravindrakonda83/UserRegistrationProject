import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve input values
        String employeeName = request.getParameter("employeeName");
        String employeeId = request.getParameter("employeeId");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String department = request.getParameter("department");

        // Create an employee object 
        Employee employee = new Employee(employeeName, employeeId, email, phone, department);

        // Get the session and retrieve the list of employees
        HttpSession session = request.getSession();
        List<Employee> employeeList = (List<Employee>) session.getAttribute("employees");

        // If the list is null, initialize it
        if (employeeList == null) {
            employeeList = new ArrayList<>();
        }

        // Add the new employee to the list
        employeeList.add(employee);

        // Save the list back in the session
        session.setAttribute("employees", employeeList);

     // Display the details in the browser
        response.setContentType("text/html");
        response.getWriter().println("<html>");
        response.getWriter().println("<head>");
        response.getWriter().println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css'>");
        response.getWriter().println("</head>");
        response.getWriter().println("<body>");
        response.getWriter().println("<div class='container mt-5'>");
        response.getWriter().println("<h1 class='text-center'>Employee Data Submitted</h1>");
        response.getWriter().println("<h3 class='mt-4'>All Employees:</h3>");
        response.getWriter().println("<table class='table table-striped table-bordered mt-3'>");
        response.getWriter().println("<thead>");
        response.getWriter().println("<tr>");
        response.getWriter().println("<th>Name</th>");
        response.getWriter().println("<th>ID</th>");
        response.getWriter().println("<th>Email</th>");
        response.getWriter().println("<th>Phone</th>");
        response.getWriter().println("<th>Department</th>");
        response.getWriter().println("</tr>");
        response.getWriter().println("</thead>");
        response.getWriter().println("<tbody>");
        
        // Display all employees stored in the session
        for (Employee emp : employeeList) {
            response.getWriter().println("<tr>");
            response.getWriter().println("<td>" + emp.getName() + "</td>");
            response.getWriter().println("<td>" + emp.getId() + "</td>");
            response.getWriter().println("<td>" + emp.getEmail() + "</td>");
            response.getWriter().println("<td>" + emp.getPhone() + "</td>");
            response.getWriter().println("<td>" + emp.getDepartment() + "</td>");
            response.getWriter().println("</tr>");
        }
        response.getWriter().println("</tbody>");
        response.getWriter().println("</table>");
        response.getWriter().println("<a href='index.html' class='btn btn-primary'>Add Another Employee</a>");
        response.getWriter().println("</div>");
        response.getWriter().println("</body>");
        response.getWriter().println("</html>");
    }
}
// Employee class to represent employee details
class Employee {
    private String name;
    private String id;
    private String email;
    private String phone;
    private String department;

    public Employee(String name, String id, String email, String phone, String department) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDepartment() {
        return department;
    }
}
