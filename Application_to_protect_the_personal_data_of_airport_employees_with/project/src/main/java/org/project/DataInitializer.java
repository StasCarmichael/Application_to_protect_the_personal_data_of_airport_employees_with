package org.project;

import org.hibernate.SessionFactory;
import org.project.bd.dao.EmployeeDAO;
import org.project.bd.dao.LoginCredentialsDAO;
import org.project.bd.entities.Employee;
import org.project.bd.entities.LoginCredentials;
import org.project.services.EncryptionUtil;
import org.project.services.LoginCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;


public class DataInitializer {
    private static final String[] departments = {"HR", "IT", "Finance", "Marketing", "Sales"};

    public static void init() throws Exception {
        EmployeeDAO empl = new EmployeeDAO();
        LoginCredentialsDAO cred = new LoginCredentialsDAO();
        EncryptionUtil encryptionUtil = new EncryptionUtil();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        for (int i = 1; i <= 30; i++) {
            Employee employee = new Employee();
            employee.setName("Employee " + i);
            employee.setPosition("Position " + i);
            employee.setDepartment(departments[getRandom(0,departments.length-1)]);
            employee.setEmail( "employee" + i + "@example.com");
            employee.setPhone(
                    "123-"+getRandom(100,999)+"-"+getRandom(100,999) );
            employee.setHireDate(LocalDate.now().minusYears(getRandom(1, 15)).minusDays(i));
            employee.setBirthDate(LocalDate.now().minusYears(getRandom(30, 60)).minusDays(i));
            empl.saveEmployee(employee);

            LoginCredentials loginCredentials = new LoginCredentials();
            loginCredentials.setEmployee(employee);
//            loginCredentials.setEmployee(employee);
//            loginCredentials.setEmployeeId(employee.getEmployeeId());
            loginCredentials.setUsername("user" + i);
            loginCredentials.setPasswordHash(encoder.encode("pass" + i));
            loginCredentials.setLastLoginDate(Date.from(LocalDate.now().minusDays(i).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            loginCredentials.setLoginAttempts(0);
            cred.save(loginCredentials);
        }
    }

    static private int getRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}
