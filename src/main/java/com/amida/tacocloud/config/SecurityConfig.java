package com.amida.tacocloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails user1 = User
                .withUsername("amida")
                .password(encoder.encode("amida123"))
                .authorities("ROLE_USER")
                .build();

        UserDetails user2 = User
                .withUsername("envy")
                .password(encoder.encode("envy123"))
                .authorities("ROLE_USER")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }
}
