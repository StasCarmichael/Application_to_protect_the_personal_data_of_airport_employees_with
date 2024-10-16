package org.project.web;


import org.project.bd.dao.LoginCredentialsDAO;
import org.project.bd.entities.LoginCredentials;
import org.project.services.LoginCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginCredentialsService loginCredentialsService;

    @Autowired
    private PasswordEncoder passwordEncoder;


//
//    @Autowired
//    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserDetailsService)
//                .passwordEncoder(passwordEncoder());
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/public/**").permitAll()
                .antMatchers("/private/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()

                .formLogin(withDefaults())
//                .loginPage(withDefaults())
//                .formLogin()
//                .defaultSuccessUrl("/", true) //
//                .permitAll()
//                .and()
                .logout()
                .permitAll();


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginCredentialsService).passwordEncoder(passwordEncoder);
//                .inMemoryAuthentication()
//
//                .withUser("user").password(passwordEncoder().encode("123"))
//                .roles("USER")
//                .and()
//                .withUser("admin").password(passwordEncoder()
//                        .encode("1234"))
//                .roles("USER", "ADMIN");


    }


}
