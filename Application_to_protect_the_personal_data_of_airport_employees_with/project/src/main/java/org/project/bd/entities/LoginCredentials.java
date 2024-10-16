package org.project.bd.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "login_credentials")
public class LoginCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_id")
    private int loginId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee")
    private Employee employee;


    @Column(name = "username")
    private String username;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "last_login_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginDate;

    @Column(name = "login_attempts")
    private int loginAttempts;

    // getters and setters

    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }

//    public Employee getEmployee() {
//        return employee;
//    }

//    public void setEmployee(Employee employee) {
//        this.employee = employee;
//        this.employeeId = employee.getEmployeeId();
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }


    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"loginId\":").append(loginId).append(",");

            sb.append("\"employee\":").append(employee.toString()).append(","); // Handle employee object accordingly


        sb.append("\"username\":\"").append(username).append("\",");
        sb.append("\"passwordHash\":\"").append(passwordHash).append("\",");
        sb.append("\"lastLoginDate\":\"").append(lastLoginDate).append("\","); // Convert date to string if needed
        sb.append("\"loginAttempts\":").append(loginAttempts);
        sb.append("}");
        return sb.toString();
    }
}
