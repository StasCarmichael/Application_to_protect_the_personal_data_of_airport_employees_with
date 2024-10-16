package org.project.bd.entities;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private int employeeId;

    @Column(name = "name")
    private String name;

    @Column(name = "position")
    private String position;

    @Column(name = "department")
    private String department;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "hire_date")
    @Temporal(TemporalType.DATE)
    private Date hireDate;


    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    // getters and setters

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = Date.from(hireDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public Date getBirthDate() {
        return birthDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"employeeId\":").append(employeeId).append(",");
        sb.append("\"name\":\"").append(name).append("\",");
        sb.append("\"position\":\"").append(position).append("\",");
        sb.append("\"department\":\"").append(department).append("\",");
        sb.append("\"email\":\"").append(email).append("\",");
        sb.append("\"phone\":\"").append(phone).append("\",");
        sb.append("\"hireDate\":\"").append(hireDate).append("\",");
        sb.append("\"birthDate\":\"").append(dateFormat.format(birthDate)).append("\"");
        sb.append("}");
        return sb.toString();
    }

}
