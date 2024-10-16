package org.project.services;

import org.project.bd.dao.LoginCredentialsDAO;
import org.project.bd.entities.Employee;
import org.project.bd.entities.LoginCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class LoginCredentialsService implements UserDetailsService {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LoginCredentialsDAO loginCredentialsDAO;

    @Transactional
    public void initializeLoginCredentials(Employee employee, String username, String password) {
        LoginCredentials credentials = new LoginCredentials();
        credentials.setUsername(username);
        credentials.setPasswordHash(passwordEncoder.encode(password));
        credentials.setLastLoginDate(null); // Set to null for initial login
        credentials.setLoginAttempts(0); // Initial login attempts

        loginCredentialsDAO.save(credentials);
    }

    public LoginCredentials findByUsername(String username) {
        return loginCredentialsDAO.findByUsername(username);
    }

    public void addLoginCredentials(LoginCredentials credentials) {
        loginCredentialsDAO.save(credentials);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginCredentials credentials = loginCredentialsDAO.findByUsername(username);
        if (credentials == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(credentials.getUsername())
                .password(credentials.getPasswordHash()) // Assuming you store password hash in passwordHash field
                .roles("USER") // You may set roles or authorities here
                .build();
    }
}
