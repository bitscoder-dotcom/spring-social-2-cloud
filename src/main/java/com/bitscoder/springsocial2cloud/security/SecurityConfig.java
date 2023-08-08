package com.bitscoder.springsocial2cloud.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collections;

@Configuration
@Log4j2
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService inMemoryUsers(){
        InMemoryUserDetailsManager users = new InMemoryUserDetailsManager();

        var david = new User("david", passwordEncoder().encode("0000"), Collections.emptyList());
        var rome = User.builder()
                .username("rome")
                .password(passwordEncoder().encode("00000"))
                .authorities("USER")
                .build();
        users.createUser(david);
        users.createUser(rome);
        return users;
    }

    @Bean
    ApplicationListener<AuthenticationSuccessEvent> successLogger() {
        return event -> {
          log.info("success: {}", event.getAuthentication());
        };
    }
}
