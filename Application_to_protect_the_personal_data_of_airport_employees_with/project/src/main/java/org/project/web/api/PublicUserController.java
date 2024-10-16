package org.project.web.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.project.bd.dao.EmployeeDAO;
import org.project.bd.dao.LoginCredentialsDAO;
import org.project.bd.entities.Employee;
import org.project.bd.entities.LoginCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/api/employees")
public class PublicUserController {

    @Autowired
    private LoginCredentialsDAO loginCredentialsDAO;
    @Autowired
    private EmployeeDAO employeeDAO;

    @GetMapping("/{id}/creds")
    public String getCreds(@PathVariable int id) {
        LoginCredentials res = loginCredentialsDAO.getCredentialsByEmployee(employeeDAO.getEmployeeById(id));
        if(res != null) {
            return res.toString();
        } else {
            return "User not found";
        }
    }

    @GetMapping("/password")
    public String getPasswordByUsername(@RequestParam String username) {
        LoginCredentials credentials = loginCredentialsDAO.findByUsername(username);
        if (credentials != null) {
            return credentials.getPasswordHash();
        } else {
            return "User not found";
        }
    }

    @GetMapping
    public String getEmployees(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "per_page", defaultValue = "5") int perPage,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "position", defaultValue = "") String position,
            @RequestParam(value = "department", defaultValue = "") String department,
            @RequestParam(value = "birthDate", defaultValue = "") String birthDate,
            @RequestParam(value = "email", defaultValue = "") String email,
            @RequestParam(value = "phone", defaultValue = "") String phone) {

        List<Employee> res = employeeDAO
                .getEmployeesWithFilters(page, perPage, name, position, department, birthDate, email, phone);
            String str = res.toString();
            return str;

    }

    @GetMapping("/count/{perPage}")
    public String getEmployeesCount(@PathVariable int perPage) {
        return "{ \"totalPages\": " +(int) (employeeDAO.getTotalEmployees()/perPage)+"}";
    }
}