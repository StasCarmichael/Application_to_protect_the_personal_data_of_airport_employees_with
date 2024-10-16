package org.project.bd;

import org.project.bd.dao.EmployeeDAO;
import org.project.bd.entities.Employee;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();

        // Save new employee
        Employee newEmployee = new Employee();
        newEmployee.setName("John Doe");
        newEmployee.setPosition("Manager");
        newEmployee.setDepartment("IT");
        newEmployee.setEmail("john.doe@example.com");
        newEmployee.setPhone("123-456-7890");
        newEmployee.setHireDate(LocalDate.now());
        newEmployee.setBirthDate(LocalDate.of(1990, 1, 1));
        dao.saveEmployee(newEmployee);

        // Get employee by ID
        Employee retrievedEmployee = dao.getEmployeeById(newEmployee.getEmployeeId());
        System.out.println("Retrieved Employee: " + retrievedEmployee);

        // Update employee
        retrievedEmployee.setPosition("Senior Manager");
        dao.updateEmployee(retrievedEmployee);

        // Get all employees
        List<Employee> allEmployees = dao.getAllEmployees();
        System.out.println("All Employees: ");
        for (Employee employee : allEmployees) {
            System.out.println(employee);
        }

        // Delete employee by ID
        int employeeIdToDelete = retrievedEmployee.getEmployeeId();
        dao.deleteEmployee(employeeIdToDelete);

        dao.close();
    }
}
