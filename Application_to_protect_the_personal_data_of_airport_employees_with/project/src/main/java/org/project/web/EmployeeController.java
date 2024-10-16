package org.project.web;

import org.project.bd.dao.EmployeeDAO;
import org.project.bd.entities.Employee;
import org.project.web.responses.EmployeeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeDAO employeeDAO;

    // Додавання нового співробітника
    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
        try {
            employeeDAO.saveEmployee(employee);
            return new ResponseEntity<>("Employee added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Редагування існуючого співробітника
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        try {
            employeeDAO.updateEmployee(employee);
            return new ResponseEntity<>("Employee updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Видалення співробітника
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable int id) {
        try {
            employeeDAO.deleteEmployee(id);
            return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Отримання списку співробітників з пагінацією та фільтрацією
    @GetMapping
    public ResponseEntity<?> getEmployees(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "per_page", defaultValue = "5") int perPage,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "position", required = false) String position,
            @RequestParam(name = "department", required = false) String department,
            @RequestParam(name = "birthDate", required = false) String birthDate,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "phone", required = false) String phone) {

        try {
            List<Employee> employees = employeeDAO.getEmployeesWithFilters(page, perPage, name, position, department, birthDate, email, phone);
            int totalEmployees = employeeDAO.getTotalEmployees();
            int totalPages = (int) Math.ceil((double) totalEmployees / perPage);
            return ResponseEntity.ok().body(new EmployeeResponse(employees, totalPages, page));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }












}
