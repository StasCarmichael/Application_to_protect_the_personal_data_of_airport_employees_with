package org.project.web.responses;

import org.project.bd.entities.Employee;

import java.util.List;

import java.util.List;

public class EmployeeResponse {
    private List<Employee> employees;
    private int totalPages;
    private int currentPage;

    public EmployeeResponse(List<Employee> employees, int totalPages, int currentPage) {
        this.employees = employees;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
